package com.thehomy.Fragment

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thehomy.Adapater.Recipe_Adapter
import com.thehomy.Adapater.Service_Adapter
import com.thehomy.models.RecipeModel
import com.thehomy.models.ServiceModel
import com.thehomy.R
import com.thehomy.Repository.Service_Repo
import com.thehomy.ViewModels.Service_VM
import com.thehomy.ViewModels.Service_VMFactory
import com.thehomy.avatar
import com.thehomy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var serviceModel = mutableListOf<ServiceModel>()
    private val recipeModel = mutableListOf<RecipeModel>()
    private lateinit var ServiceVM: Service_VM

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private var ref = database.reference.child("user1")

    val uid = auth.currentUser?.uid

    private val avatarSelection =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedAvatar = data?.getIntExtra("selectedAvatar", R.drawable.noti)
                selectedAvatar?.let {

                    if (uid != null) {
                        ref.child(uid).child("avatar").setValue(it)
                    }
                    // Update the profile image with the selected avatar
                    // binding.profileImg.setImageResource(it)
                    Glide.with(this)
                        .load(it) // Replace with your image resource or URL
                        .apply(RequestOptions().transform(CircleCrop()))
                        .into(binding.mainToolBar.userAvatar)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment

        //profile fetch
        val userId = auth.currentUser?.uid
        profile(userId.toString())

        avatar()

        //Setting Up Loading
        binding.view.visibility = View.INVISIBLE
        binding.loading.progressTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))

        //Setting Up VM
        val repo = Service_Repo()
        ServiceVM = ViewModelProvider(this, Service_VMFactory(repo))[Service_VM::class.java]
        ServiceVM.getData()

        //Setting Up Adapter
        serviceModel = arrayListOf()
        val Service_Adapter = Service_Adapter(serviceModel)
        binding.serviceRec.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.serviceRec.adapter = Service_Adapter


        //Observing Data
        ServiceVM.ServiceLiveData.observe(viewLifecycleOwner) {
            serviceModel.clear()
            if (it != null) {
                for (items in it) {
                    serviceModel.add(items)
                }
            }
            Service_Adapter.notifyDataSetChanged()
            //Ending the Loading
            binding.view.visibility = View.VISIBLE
            binding.loading.visibility = View.INVISIBLE
        }

//        binding.mainToolBar.userName.text =


        recipeModel.clear()
        recipeModel.add(RecipeModel("Aloo Tikki", "Indian", R.drawable.sample_img))
        recipeModel.add(RecipeModel("Aloo Tikki", "Indian", R.drawable.sample_img))
        recipeModel.add(RecipeModel("Aloo Tikki", "Indian", R.drawable.sample_img))

        binding.recipeRec.layoutManager = LinearLayoutManager(context)
        binding.recipeRec.adapter = Recipe_Adapter(recipeModel)
        return binding.root
    }

    private fun avatar() {
        binding.mainToolBar.userAvatar.setOnClickListener {
            val intent = Intent(context, avatar::class.java)
            avatarSelection.launch(intent)

        }
    }

    private fun profile(userId: String) {
        database.reference.child("user1").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("userName").getValue(String::class.java)
                    val address = snapshot.child("userAddress").getValue(String::class.java)
                    val city = snapshot.child("userCity").getValue(String::class.java)
                    val avatar = snapshot.child("avatar").getValue(Int::class.java)

                    if(avatar!=null){
                        Glide.with(requireContext())
                            .load(avatar)
                            .apply(RequestOptions().transform(CircleCrop()))
                            .into(binding.mainToolBar.userAvatar)
                    }


                    binding.mainToolBar.userName.text = name
                    binding.mainToolBar.userLocation.text = "$address, $city"
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

}
package com.lazday.mainbadminton.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lazday.mainbadminton.R
import com.lazday.mainbadminton.activity.AllActivity
import com.lazday.mainbadminton.activity.UpdateActivity
import com.lazday.mainbadminton.adapter.PlayingAdapter
import com.lazday.mainbadminton.adapter.PlayerAdapter
import com.lazday.mainbadminton.database.DatabaseClient
import com.lazday.mainbadminton.database.TaskDao
import com.lazday.mainbadminton.database.TaskModel
import com.lazday.mainbadminton.databinding.FragmentHomeBinding
import com.lazday.mainbadminton.util.dateCompleted
import com.lazday.mainbadminton.util.dateToLong
import com.lazday.mainbadminton.util.dateToday

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: TaskDao
    private lateinit var adapterPlaying: PlayingAdapter
    private lateinit var adapterPlayer: PlayerAdapter
    private lateinit var taskSelected: TaskModel
    private var isHideGrade = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        database = DatabaseClient.getService(requireActivity()).taskDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        setupData()
    }

    private fun setupList(){
        adapterPlaying = PlayingAdapter(arrayListOf(), object: PlayingAdapter.AdapterListener {
            override fun onPlay(taskModel: TaskModel) {
                taskSelected = taskModel
                taskSelected.countPlay = 0
                taskSelected.isPlaying = false
                Thread {
                    database.update(taskSelected)
                }.start()
            }
            override fun onCountAdd(taskModel: TaskModel) {
                taskSelected = taskModel
                taskSelected.lastUpdate = taskModel.updated
                taskSelected.updated = dateToLong( dateCompleted()!! )
                taskSelected.countPlay += 1
                Thread {
                    database.update(taskSelected)
                }.start()
            }
            override fun onCountMin(taskModel: TaskModel) {
                taskSelected = taskModel
                if (taskSelected.countPlay > 0) {
                    taskSelected.updated = taskModel.lastUpdate
                    taskSelected.countPlay -= 1
                    Thread {
                        database.update(taskSelected)
                    }.start()
                }
            }
        })
        binding.listTask.adapter = adapterPlaying
        adapterPlayer = PlayerAdapter(arrayListOf(), object: PlayerAdapter.AdapterListener {
            override fun onPlay(taskModel: TaskModel) {
                taskSelected = taskModel
                taskSelected.isPlaying = true
                Thread {
                    database.update(taskSelected)
                }.start()
            }
            override fun onDetail(taskModel: TaskModel) {
                startActivity(
                    Intent(requireActivity(), UpdateActivity::class.java)
                        .putExtra("intent_task", taskModel )
                )
            }
        })
        binding.listTaskCompleted.adapter = adapterPlayer
    }

    private fun setupListener(){
        binding.imgAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
        binding.textAll.setOnClickListener {
            startActivity(Intent(requireActivity(), AllActivity::class.java))
        }
        binding.imgMode.apply {
            setImageResource(R.drawable.ic_grade)
            setOnClickListener {
                isHideGrade = !isHideGrade
                adapterPlaying.hideGrade(isHideGrade)
                setImageResource(if (isHideGrade) R.drawable.ic_grade else R.drawable.ic_update)
            }
        }
        binding.textCompleted.setOnClickListener {
            when (binding.listTaskCompleted.visibility) {
                View.VISIBLE -> {
                    binding.listTaskCompleted.visibility = View.GONE
                    binding.imageCompleted.setImageResource(R.drawable.ic_arrow_right)
                }
                View.GONE -> {
                    binding.listTaskCompleted.visibility = View.VISIBLE
                    binding.imageCompleted.setImageResource(R.drawable.ic_arrow_down)
                }
            }
        }
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    private fun setupData(){
        binding.textToday.text = dateToday()
        database.allPlayer(true).observe(viewLifecycleOwner, Observer {
            adapterPlaying.addList( it )
            binding.labelTask.text = "Mabar Hari Ini (${it.size})"
            binding.imgMode.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            binding.textAlert.apply {
                text = "Tidak ada pemain hari ini"
                visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        })
        database.allPlayer(false).observe(viewLifecycleOwner, Observer {
            adapterPlayer.addList( it )
            val visibleCompleted = if (it.isEmpty()) View.GONE else View.VISIBLE
            binding.textCompleted.apply {
                visibility = visibleCompleted
                text = "Semua Pemain (${it.size})"
            }
            binding.imageCompleted.visibility = visibleCompleted
        })
    }
}
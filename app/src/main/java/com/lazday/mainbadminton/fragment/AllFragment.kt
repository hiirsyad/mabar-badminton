package com.lazday.mainbadminton.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lazday.mainbadminton.R
import com.lazday.mainbadminton.activity.UpdateActivity
import com.lazday.mainbadminton.adapter.PlayingAdapter
import com.lazday.mainbadminton.adapter.PlayerAdapter
import com.lazday.mainbadminton.database.DatabaseClient
import com.lazday.mainbadminton.database.TaskDao
import com.lazday.mainbadminton.database.TaskModel
import com.lazday.mainbadminton.databinding.FragmentAllBinding

class AllFragment : Fragment() {

    private lateinit var binding: FragmentAllBinding
    private lateinit var database: TaskDao
    private lateinit var adapterTask: PlayingAdapter
    private lateinit var adapterTaskCompleted: PlayerAdapter
    private lateinit var taskSelected: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(inflater, container, false)
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

    private fun setupList() {
//        adapterTask = PlayingAdapter(arrayListOf(), object: PlayingAdapter.AdapterListener {
//            override fun onCompleted(taskModel: TaskModel) {
//                taskSelected = taskModel
//                taskSelected.isPlaying = true
//                Thread {
//                    database.update(taskSelected)
//                }.start()
//            }
//            override fun onDetail(taskModel: TaskModel) {
//                startActivity(
//                    Intent(requireActivity(), UpdateActivity::class.java)
//                        .putExtra("intent_task", taskModel )
//                )
//            }
//        })
//        binding.listTask.adapter = adapterTask
//        adapterTaskCompleted = PlayerAdapter(arrayListOf(), object: PlayerAdapter.AdapterListener {
//            override fun onClick(taskModel: TaskModel) {
//                taskSelected = taskModel
//                taskSelected.isPlaying = false
//                Thread {
//                    database.update(taskSelected)
//                }.start()
//            }
//        })
//        binding.listTaskCompleted.adapter = adapterTaskCompleted
    }

    private fun setupListener(){
        binding.imageMenu.setOnClickListener {
            PopupMenu(requireActivity(), it).apply {
                setOnMenuItemClickListener { item ->
                    when (item?.itemId) {
                        R.id.action_new -> {
                            findNavController().navigate(R.id.action_allFragment_to_addFragment)
                            true
                        }
                        R.id.action_delete -> {
                            Thread { database.deleteCompleted() }.start()
                            true
                        }
                        R.id.action_delete_all -> {
                            Thread { database.deleteAll() }.start()
                            true
                        }
                        else -> false
                    }
                }
                inflate(R.menu.menu_task)
                show()
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
    }

    private fun setupData(){
        database.taskAll( false ).observe(viewLifecycleOwner, Observer {
            adapterTask.addList( it )
            binding.textAlert.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        })
        database.taskAll( true ).observe(viewLifecycleOwner, Observer {
            adapterTaskCompleted.addList( it )
            val visibleCompleted = if (it.isEmpty()) View.GONE else View.VISIBLE
            binding.textCompleted.visibility = visibleCompleted
            binding.imageCompleted.visibility = visibleCompleted
        })
    }
}
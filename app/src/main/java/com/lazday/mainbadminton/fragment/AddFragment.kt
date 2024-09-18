package com.lazday.mainbadminton.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.lazday.mainbadminton.database.DatabaseClient
import com.lazday.mainbadminton.database.TaskDao
import com.lazday.mainbadminton.database.TaskModel
import com.lazday.mainbadminton.databinding.FragmentAddBinding
import com.lazday.mainbadminton.util.dateCompleted
import com.lazday.mainbadminton.util.dateToDialog
import com.lazday.mainbadminton.util.dateToLong
import com.lazday.mainbadminton.util.dateToString
import com.lazday.mainbadminton.util.dateToday

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var database: TaskDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        database = DatabaseClient.getService(requireActivity()).taskDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener(){

        binding.textDate.text = dateToday()
        binding.labelDate.setOnClickListener {
            val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                binding.textDate.text = dateToString(year, month, day)
            }
            dateToDialog(
                requireActivity(),
                datePicker
            )!!.show()
        }

        binding.buttonSave.setOnClickListener {
//            val myDate = dateToLong( binding.textDate.text.toString() )
            val myDate = dateToLong( dateCompleted()!! )
            val taskModel = TaskModel(
                id = 0,
                name = binding.editTask.text.toString(),
                grade = binding.editGrade.text.toString(),
                countPlay = 0,
                isPlaying = false,
                updated = myDate,
                lastUpdate = myDate
            )
            addTask( taskModel )
        }
    }

    private fun addTask(taskModel: TaskModel){
        Thread {
            database.insert( taskModel )
            requireActivity().runOnUiThread {
                Toast.makeText(
                    requireActivity(),
                    "Berhasil ditambahkan",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
        }.start()
    }
}
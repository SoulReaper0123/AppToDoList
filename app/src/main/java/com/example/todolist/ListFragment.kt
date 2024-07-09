package com.example.todolist

import ListAdapter
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.room.Task
import com.example.todolist.room.TaskViewModel

class ListFragment : Fragment() {

    private lateinit var aTaskViewModel: TaskViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val buttonAdd: Button = view.findViewById(R.id.buttonAdd)
        val inputList: EditText = view.findViewById(R.id.inputList)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        adapter = ListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        aTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        aTaskViewModel.readAllData.observe(viewLifecycleOwner, Observer { task ->
            adapter.setData(task)
        })


        buttonAdd.setOnClickListener {
            val taskName = inputList.text.toString()
            if (TextUtils.isEmpty(taskName)) {
                Toast.makeText(requireContext(), "Please enter a text", Toast.LENGTH_SHORT).show()
            } else {
                insertDataToDatabase(taskName)
                inputList.text.clear()
            }
        }

        return view
    }

    private fun insertDataToDatabase(taskName: String) {
        val task = Task(0, taskName)
        aTaskViewModel.addList(task)
        Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
    }
}

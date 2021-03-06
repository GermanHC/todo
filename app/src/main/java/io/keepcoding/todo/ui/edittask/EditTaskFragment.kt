package com.germanhc.todo.ui.edittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.germanhc.todo.R
import com.germanhc.todo.data.model.Task
import com.germanhc.todo.ui.tasks.TaskViewModel
import com.germanhc.todo.util.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_edit_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTaskFragment : BottomSheetDialog() {

    companion object {
        const val PARAM_TASK = "task"

        fun newInstance(task: Task): EditTaskFragment =
            EditTaskFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_TASK, task)
                }
            }
    }

    val taskViewModel: TaskViewModel by viewModel()

    var task: Task? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        task = arguments?.let {
            it.getParcelable(PARAM_TASK)
        }
        if (task == null) {
            dismiss()
        }

        setUp()
    }

    private fun setUp() {
        fillData()
        bindEvents()
        bindActions()
    }

    private fun bindEvents() {
        with (taskViewModel) {
            taskUpdatedEvent.observe(this@EditTaskFragment, Observer {
                dismiss()
            })
        }
    }

    private fun fillData() {
        requireNotNull(task) {
            "Task is null dialog should be closed"
        }

        inputTaskContent.setText(task!!.content)
        checkHighPriority.isChecked = task!!.isHighPriority
    }

    private fun bindActions() {
        buttonSaveTask.setOnClickListener {
            val newTask = task!!.copy(
                content = inputTaskContent.text.toString(),
                isHighPriority = checkHighPriority.isChecked)

            taskViewModel.updateTask(newTask)
        }
    }

}
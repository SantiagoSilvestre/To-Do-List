package me.san.listtodo.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import me.san.listtodo.databinding.ActivityAddTaskBinding
import me.san.listtodo.extensions.format
import me.san.listtodo.extensions.text
import me.san.listtodo.model.TaskModel
import me.san.listtodo.viewmodel.MainViewModel
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var mViewModel: MainViewModel
    private var mIdTask = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        loadFromActivity()
        observer()
        insertListerners()

    }

    private fun loadFromActivity() {
        if (intent.hasExtra(TASK_ID)) {
            val intExtra = intent.getIntExtra(TASK_ID, 0)
            if (intExtra != 0) {
                mIdTask = intExtra
                mViewModel.load(mIdTask)
            }
        }
    }

    private fun insertListerners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker
                .Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute

                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btnNewTask.setOnClickListener {
            handleSave()
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnCancel.setOnClickListener { finish() }
    }


    fun observer() {
        mViewModel.task.observe(this, {
            binding.tilTitle.text = it.title
            binding.tilDate.text = it.date
            binding.tilHour.text = it.hour
            binding.tilDescricao.text = it.descricao
        })
    }


    fun handleSave() {
        val task = TaskModel().apply {
            this.id = mIdTask
            this.date = binding.tilDate.text
            this.title = binding.tilTitle.text
            this.descricao = binding.tilDescricao.text
            this.hour = binding.tilHour.text
        }
        mViewModel.save(task)
    }



    companion object {
        const val TASK_ID = "task_id"
    }

}
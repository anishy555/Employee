package com.ajm.employee

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajm.employee.database.EmployeeData
import com.ajm.employee.databinding.EmployeeRowListBinding
import kotlinx.android.synthetic.main.employee_row_list.view.*

/**
 * Created by Anish on 8/22/2020.
 */
class EmployeeListAdapter(
    mainActivity: MainActivity,
    employeeList: List<EmployeeData>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context: Context?
    private val employeeList: List<EmployeeData>?
    //private val employeeList: String
    lateinit var employeeRowListBinding: EmployeeRowListBinding

    init {
        this.context = mainActivity;
        this.employeeList = employeeList;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        employeeRowListBinding =   DataBindingUtil.inflate(inflater,R.layout.employee_row_list, parent, false)
        return ViewHolder(
            employeeRowListBinding
        )
    }

    override fun getItemCount(): Int {
        return employeeList?.size!!
    }

    override fun onBindViewHolder(viewholder: RecyclerView.ViewHolder, position: Int) {
        val holder: ViewHolder =   viewholder as ViewHolder
        Log.d("Name", employeeList?.get(position)?.name.toString())

        holder.itemView.employeeName_text.text = employeeList?.get(position)?.name.toString()
        holder.itemView.company_name_text.text = employeeList?.get(position)?.company_name.toString()

    }

   inner class ViewHolder(itemView: EmployeeRowListBinding) : RecyclerView.ViewHolder(itemView.root) {

        init {

            //itemView.employeeNameText.text = employeeList?.get(1)?.name
            //itemView.companyNameText.text = employeeList?.get(1)?.company_name
        }
    }
}
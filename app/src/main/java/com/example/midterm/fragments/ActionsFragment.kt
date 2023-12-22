package com.example.midterm.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.midterm.R
import com.example.midterm.models.Balance
import com.example.midterm.repository.Repository
import com.example.midterm.repository.ViewModel
import com.example.midterm.repository.ViewModelFactory

class ActionsFragment : Fragment() {

    private lateinit var withdraw: Button
    private lateinit var balance: Button
    private lateinit var deposit : Button
    private lateinit var exit : Button
    private lateinit var display : TextView
    private lateinit var amountEditText : EditText
    private lateinit var repository: Repository
    private lateinit var viewModel : ViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private var balanceAmount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments;

        repository = Repository()
        viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_actions, container, false)
        withdraw = view.findViewById(R.id.withdraw)
        deposit = view.findViewById(R.id.deposit)
        balance = view.findViewById(R.id.show_balance)
        display = view.findViewById(R.id.info_textview)
        amountEditText = view.findViewById(R.id.amountEditText)
        exit = view.findViewById(R.id.exit)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        balance.setOnClickListener {
            viewModel.balance()
            viewModel.balance.observe(viewLifecycleOwner) { response ->
                if (!response.isSuccessful) {
                } else {
                    val balanceAmount = response.body()!!
                    showBalance(balanceAmount.balance.toString())
                }
            }
        }
        deposit.setOnClickListener {
            if(amountEditText.text == null || amountEditText.text.isEmpty()){
                Toast.makeText(activity,"Введите сумму", Toast.LENGTH_LONG).show()
            } else {
                val deposit = Balance(readInt())
                viewModel.deposit(deposit)
                viewModel.deposit.observe(viewLifecycleOwner) { response ->
                    if (!response.isSuccessful) {
                    } else {
                        val user = response.body()
                        showBalance(user!!.balance.toString())
                    }
                }
            }
        }
        withdraw.setOnClickListener {
            if(amountEditText.text == null || amountEditText.text.isEmpty()){
                Toast.makeText(activity,"Введите сумму", Toast.LENGTH_LONG).show()
            } else if (balanceAmount < readInt()){
                Toast.makeText(activity,"Недостаточно средств", Toast.LENGTH_LONG).show()
            } else {
                val amount = Balance(readInt())
                viewModel.withdraw(amount)
                viewModel.withdraw.observe(viewLifecycleOwner) { response ->
                    if (!response.isSuccessful) {
                    } else {
                        val user = response.body()
                        showBalance(user!!.balance.toString())
                    }
                }
            }
            //showBalance()
        }
        exit.setOnClickListener {
            val fm = activity?.supportFragmentManager
            fm?.beginTransaction()
                ?.replace(R.id.fragments, AuthFragment())
                ?.addToBackStack(null)?.commit()
        }
    }

    private fun showBalance(balAmount:String) {
        display.text = "Your balance is $balAmount"
        balanceAmount = balAmount.toInt()
    }

        private fun readInt(): Int {
        val num1 = amountEditText.text.toString()
        return num1.toIntOrNull() ?: 0
    }
}
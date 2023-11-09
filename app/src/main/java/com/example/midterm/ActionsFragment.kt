package com.example.midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ActionsFragment : Fragment() {

    private lateinit var withdraw: Button
    private lateinit var balance: Button
    private lateinit var deposit : Button
    private lateinit var exit : Button
    private lateinit var display : TextView
    private lateinit var amountEditText : EditText
    private lateinit var users : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments;

        if(bundle != null){
            println(bundle);
            users = bundle.getSerializable("user") as User
        }
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
            showBalance()
        }
        deposit.setOnClickListener {
            val amount = readInt()
            users.balance += amount
            showBalance()
        }
        withdraw.setOnClickListener {
            val amount = readInt()
            users.balance -= amount
            showBalance()
        }
        exit.setOnClickListener {
            val fm = activity?.supportFragmentManager
            fm?.beginTransaction()
                ?.replace(R.id.fragments, AuthFragment())
                ?.addToBackStack(null)?.commit()
        }
    }

    private fun showBalance() {
        display.text = "Your balance is ${users.balance}"
    }

        private fun readInt(): Int {
        val num1 = amountEditText.text.toString()
        return num1.toIntOrNull() ?: 0
    }
}
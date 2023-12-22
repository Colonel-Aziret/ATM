package com.example.midterm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.midterm.R
import com.example.midterm.models.Balance
import com.example.midterm.models.User
import com.example.midterm.repository.Repository
import com.example.midterm.repository.ViewModel
import com.example.midterm.repository.ViewModelFactory


class AuthFragment : Fragment() {

    private var users : User? = null
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton : Button
    private lateinit var resultTextView : TextView
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        loginEditText = view.findViewById(R.id.loginEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        loginButton = view.findViewById(R.id.loginButton)
        resultTextView = view.findViewById(R.id.resultTextView)
        resultTextView.visibility = View.GONE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener {
            resultTextView.visibility = View.GONE
            auth()
        }
    }

    private fun auth() {
        val login = loginEditText.text.toString()
        val password = passwordEditText.text.toString()

        users = User(login, password)

        val repository = Repository()
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ViewModel::class.java]
        viewModel.authUser(users!!)
        viewModel.myAuthResponse.observe(viewLifecycleOwner, Observer { response ->    if (!response.isSuccessful) {
            Log.i("ANSWER", "Code:" + response.code())
            Log.e("ANSWER", "Code:" + response.body())
            Log.i("ANSWER", "Code:" + response.body().toString())
            isDone()
        } else {
            val balance = response.body()
            runApp(balance!!)
            }
        })
    }

    private fun isDone() {
        resultTextView.visibility = View.VISIBLE
    }

    private fun runApp(balance:Balance) {
        val bundle = Bundle()
        bundle.putSerializable("balance", balance)
        val fragment = ActionsFragment()
        fragment.arguments = bundle
        val fm = activity?.supportFragmentManager
        fm?.beginTransaction()
            ?.replace(R.id.fragments, fragment)
            ?.addToBackStack(null)?.commit()
    }

}
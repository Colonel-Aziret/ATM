package com.example.midterm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment


class AuthFragment : Fragment() {

    private val users = User("Aziret", "12345", 1000)
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton : Button
    private lateinit var resultTextView : TextView

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

        if (login == users.username && password == users.password) {
            runApp()
        } else {
            isDone()
        }
    }

    private fun isDone() {
        resultTextView.visibility = View.VISIBLE
    }

    private fun runApp() {
        val bundle = Bundle()
        bundle.putSerializable("user", users)
        val fragment = ActionsFragment()
        fragment.arguments = bundle
        val fm = activity?.supportFragmentManager
        fm?.beginTransaction()
            ?.replace(R.id.fragments, fragment)
            ?.addToBackStack(null)?.commit()
    }

}
package com.coldwizards.demoapp.instagram.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.coldwizards.coollibrary.MyDialog
import com.coldwizards.demoapp.R
import com.coldwizards.demoapp.instagram.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by jess on 19-6-18.
 */
class LoginFragment : BaseFragment() {

    private val viewmodel by lazy {
        ViewModelProviders.of(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setButtonListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seToolbarTitle("Login")

        if ((arguments?.getString("username") ?: "").isNotEmpty()) {
            user_name_et.setText(arguments?.getString("username") ?: "")
        } else {
            viewmodel.fillLastUser().observe(this, Observer {
                if (it.isEmpty()) {

                } else {
                    val user = it[0]
                    user_name_et.setText(user.username)

                    password_et.requestFocus()
                }
            })
        }
    }

    private fun setButtonListener() {
        login_btn.setOnClickListener {
            val username = user_name_et.text.toString().apply {
                if (isEmpty()) {

                    showDialog("用户名不能为空")
                    return@setOnClickListener
                }
            }
            val password = password_et.text.toString().apply {
                if (isEmpty()) {
                    showDialog("密码不能为空")
                    return@setOnClickListener
                }
            }

            viewmodel.login(this, username, password) { code, user ->
                when (code) {
                    0 -> {
                        Bundle().also {
                            it.putSerializable("user", user!!)
                            view!!.findNavController().navigate(R.id.action_loginFragment_to_postListFragment, it)
                        }


                        // 更新Activity的User,全局用户
                        (activity as InsActivity).userViewModel.setUser(user!!)
                        hideKeyboard(activity!!)
                    }
                    1 -> showDialog("密码不正确")
                    2 -> showDialog("用户名不存在")
                }
            }
        }

        register_btn.setOnClickListener {
            view!!.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun showDialog(text: String) {
        MyDialog(context!!).setMessage(text)
            .setTitle("提示")
            .setPositiveButton("确定", R.color.blue_300){}.create()
            .apply {
                window.attributes.windowAnimations = R.style.ScaleEnterDialog
                show()
            }
    }

}
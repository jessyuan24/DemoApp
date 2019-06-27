package com.coldwizards.demoapp.instagram.view

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.coldwizards.demoapp.R
import com.coldwizards.demoapp.instagram.viewmodel.RegisterViewModel
import com.coldwizards.demoapp.model.User
import com.coldwizards.demoapp.utils.showCenterToast
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * Created by jess on 19-6-20.
 */
class RegisterFragment : BaseFragment() {

    private val viewmodel by lazy {
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        seToolbarTitle("Sign Up")
        getToolbar().setDisplayHomeAsUpEnabled(true)
        getToolbar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)

        register_btn.setOnClickListener {
            val username = user_name_et.text.toString()
            val password = password_et.text.toString()
            val confirm = confirm_password_et.text.toString()

            when(viewmodel.check(username, password, confirm)) {
                0 -> {
                    viewmodel.exist(username).observe(this, Observer {
                        if (it.isEmpty()) {
                            val user = User(username, password)
                            viewmodel.register(user)
                            showCenterToast("注册成功")

                            val bundle = Bundle()
                            bundle.putString("username", username)
                            view!!.findNavController().navigate(R.id.action_registerFragment_to_loginFragment,bundle)

                            hideKeyboard(activity!!)
                        } else {
                            showCenterToast("用户名已存在")
                        }
                    })
                }
                1 -> showCenterToast("用户名不能为空")
                2 -> showCenterToast("密码不能为空")
                3 -> showCenterToast("确认密码不能为空")
                4 -> showCenterToast("密码和确认密码不一致")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            view!!.findNavController().popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }

}
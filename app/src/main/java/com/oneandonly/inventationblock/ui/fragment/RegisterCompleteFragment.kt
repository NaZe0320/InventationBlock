package com.oneandonly.inventationblock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oneandonly.inventationblock.databinding.FragmentRegisterCompletedBinding

class RegisterCompleteFragment: ContainerFragment() {

    companion object {
        fun newInstance() = RegisterCompleteFragment
    }

    private var _binding: FragmentRegisterCompletedBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterCompletedBinding.inflate(inflater,container,false)
        return binding.root
    }
}
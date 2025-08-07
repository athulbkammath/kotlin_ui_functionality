package com.eto.binding_usage_project.ui

import ValidationUtils
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eto.binding_usage_project.R
import com.eto.binding_usage_project.databinding.FragmentFormValidationBinding

class FormValidation : Fragment() {

    private lateinit var binding: FragmentFormValidationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormValidationBinding.inflate(inflater, container, false)
        titleView()
        setUpClicks()
        return binding.root
    }

    private fun titleView() {
        val headerText = arguments?.let {
            FormValidationArgs.fromBundle(it).headerText
        }
        binding.tvTitle.text = headerText
    }


    private fun setUpClicks() {
        binding.bvFragment2.setOnClickListener {
            findNavController().navigate(R.id.action_second_fragment_to_first_fragment)
        }



        binding.bvFragment.setOnClickListener {
            if (validateForm()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.formSubmittedSuccessfully),
                    Toast.LENGTH_SHORT
                ).show()
                hideKeyboard()
                hideCursor()
                clearForm()
            }
        }

        binding.bvFragment3.setOnClickListener {
            clearForm()
        }
    }

    private fun hideCursor() {
        binding.etName.clearFocus()
        binding.etPhoneNumber.clearFocus()
        binding.etEmailId.clearFocus()
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun validateForm(): Boolean {
        var isValid = true
        val name = binding.etName.text.toString().trim()
        val phone = binding.etPhoneNumber.text.toString().trim()
        val email = binding.etEmailId.text.toString().trim()


        binding.nameLayout.error = null
        binding.contactNoLayout.error = null
        binding.emailLayout.error = null

        if (!ValidationUtils.isNameValid(name)) {
            binding.nameLayout.error = getString(R.string.nameCannotBeEmpty)
            isValid = false
        }

        if (!ValidationUtils.isPhoneValid(phone)) {
            binding.contactNoLayout.error = getString(R.string.enterValid10DigitPhoneNumber)
            isValid = false
        }

        if (!ValidationUtils.isEmailValid(email)) {
            binding.emailLayout.error = getString(R.string.enterValidEmailAddress)
            isValid = false
        }

        return isValid
    }


    private fun clearForm() {
        binding.etName.text?.clear()
        binding.etPhoneNumber.text?.clear()
        binding.etEmailId.text?.clear()
        binding.contactNoLayout.error = null
        binding.nameLayout.error = null
        binding.emailLayout.error = null
    }
}

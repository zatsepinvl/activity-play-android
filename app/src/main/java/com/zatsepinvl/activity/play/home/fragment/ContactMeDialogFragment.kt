package com.zatsepinvl.activity.play.home.fragment

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.zatsepinvl.activity.play.R
import com.zatsepinvl.activity.play.android.dismissDialog
import com.zatsepinvl.activity.play.databinding.DialogContactMeBinding


class ContactMeDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogContactMeBinding.inflate(requireActivity().layoutInflater)

        binding.contactMeGihubButton.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.google_play_link))
            )
            startActivity(browserIntent)
        }

        binding.contactMeEmailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse(getString(R.string.email_intent_data))
            intent.data = data
            startActivity(intent)
        }

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setPositiveButton(R.string.ok, dismissDialog)
            .setTitle("Contact Me")
            .create()
    }
}
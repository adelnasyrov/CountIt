package com.example.letscount.minus_action

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.letscount.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Minus.newInstance] factory method to
 * create an instance of this fragment.
 */
class Minus : Fragment() {
    private val myPermissionRecordAudio = 1
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_minus, container, false)
        val score = 0
        val cardView: CardView = v.findViewById(R.id.easy)
        val cardView2: CardView = v.findViewById(R.id.medium)
        val cardView3: CardView = v.findViewById(R.id.hard)
        requestAudioPermissions()
        cardView.setOnClickListener {
            val bundle: Bundle = bundleOf("amount" to "1$score")
            Navigation.findNavController(v).navigate(R.id.action_minus_to_minus_game_1, bundle)
        }
        cardView2.setOnClickListener {
            val bundle = bundleOf("amount" to "2$score")
            Navigation.findNavController(v).navigate(R.id.action_minus_to_minus_game_1, bundle)
        }
        cardView3.setOnClickListener {
            val bundle = bundleOf("amount" to "3$score")
            Navigation.findNavController(v).navigate(R.id.action_minus_to_minus_game_1, bundle)
        }
        return v
    }

    private fun requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.RECORD_AUDIO
                )
            ) {
                Toast.makeText(
                    requireActivity(),
                    "Please grant permissions to record audio",
                    Toast.LENGTH_LONG
                )
                    .show()
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO),
                    myPermissionRecordAudio
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO),
                    myPermissionRecordAudio
                )
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Minus().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
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
 * Use the [show_memes2.newInstance] factory method to
 * create an instance of this fragment.
 */
class show_memes2 : Fragment() {
    private val MY_PERMISSIONS_RECORD_AUDIO = 1
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
        val score = 4
        val cardView: CardView = v.findViewById(R.id.easy)
        val cardView2: CardView = v.findViewById(R.id.medium)
        val cardView3: CardView = v.findViewById(R.id.hard)
        requestAudioPermissions()
        cardView.setOnClickListener {
            val bundle: Bundle = bundleOf("amount" to "1$score")
            Navigation.findNavController(v).navigate(R.id.action_minus_to_minus_game_1, bundle)
        }
        cardView2.setOnClickListener {
            val bundle = bundleOf("amount" to "2")
            Navigation.findNavController(v).navigate(R.id.action_minus_to_minus_game_1, bundle)
        }
        cardView3.setOnClickListener {
            val bundle = bundleOf("amount" to "3")
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

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO),
                    MY_PERMISSIONS_RECORD_AUDIO
                )
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO),
                    MY_PERMISSIONS_RECORD_AUDIO
                )
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Leave empty do disable back press or
                    // write your code which you want
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment show_memes2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            show_memes2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
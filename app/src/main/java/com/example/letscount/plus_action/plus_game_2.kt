package com.example.letscount.plus_action

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.letscount.R
import java.util.*
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [plus_game_2.newInstance] factory method to
 * create an instance of this fragment.
 */
class plus_game_2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var level: String? = null
    private var result: TextView? = null
    private var icmicro: ImageView? = null
    private var score_text: TextView? = null
    private var score = 0
    private var flag: Int = 0
    private var randomValue1 = Random.nextInt(0, 10)
    private var randomValue2 = Random.nextInt(0, 10)

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
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_plus_game_2, container, false)
        val number1: TextView = v.findViewById(R.id.number1)
        val number2: TextView = v.findViewById(R.id.number2)
        icmicro = v.findViewById(R.id.ic_micro)
        result = v.findViewById(R.id.result)
        level = arguments?.getString("amount")
        var range: Int = 10
        if (level == "1")
            range = 10
        if (level == "2")
            range = 100
        if (level == "3")
            range = 1000
        randomValue1 = Random.nextInt(0, range)
        randomValue2 = Random.nextInt(0, range)
        number1.text = randomValue1.toString()
        number2.text = randomValue2.toString()
        icmicro?.setOnClickListener {
            if (flag == 0) {
                flag = 1
                icmicro?.setImageResource(R.drawable.ic_mic_subst)
                Toast.makeText(requireActivity(), "Say the answer", Toast.LENGTH_SHORT).show()
                startSpeechToText()
            }
        }
        return v
    }
    private fun startSpeechToText() {
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity())
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray?) {}
            override fun onEndOfSpeech() {
                icmicro?.setImageResource(R.drawable.ic_mic)
                flag = 0
            }

            override fun onError(i: Int) {}

            override fun onResults(bundle: Bundle) {
                val results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (results != null) {
                    val toClear: String = results[0]
                    var cleared: String = ""
                    for (i in toClear.indices) {
                        if (toClear[i].toInt() in 48..57) {
                            cleared += toClear[i]
                        } else {
                            if (cleared.isNotEmpty())
                                break
                        }
                    }
                    result?.text = cleared
                    if (cleared == (randomValue1 + randomValue2).toString()) {
                        val bundles: Bundle = bundleOf("amount" to level)
                        findNavController().navigate(R.id.action_plus_game_2_to_plus_game, bundles)
                    }
                }
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle?) {}

        })
        speechRecognizer.startListening(speechRecognizerIntent)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment plus_game_2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            plus_game_2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
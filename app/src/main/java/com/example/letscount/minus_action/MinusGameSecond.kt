package com.example.letscount.minus_action

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.letscount.R
import com.google.android.material.card.MaterialCardView
import java.util.*
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MinusGameSecond.newInstance] factory method to
 * create an instance of this fragment.
 */
class MinusGameSecond : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var level: String = ""
    private var score: Int = 0
    private var result: TextView? = null
    private var microImage: ImageView? = null
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

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_minus_game_2, container, false)
        val scoreText: TextView = v.findViewById(R.id.score)
        val number1: TextView = v.findViewById(R.id.number1)
        val number2: TextView = v.findViewById(R.id.number2)
        val resultCard: MaterialCardView = v.findViewById(R.id.result_crd)
        microImage = v.findViewById(R.id.ic_micro)
        result = v.findViewById(R.id.result)
        val argumentsGot = arguments?.getString("amount").toString()
        score = argumentsGot.takeLast(argumentsGot.count() - 1).toInt()
        level = argumentsGot[0].toString()
        scoreText.text = "Score: $score"
        var range = 10
        when (level) {
            "1" -> range = 10
            "2" -> range = 100
            "3" -> range = 1000
        }
        randomValue1 = Random.nextInt(0, range)
        randomValue2 = Random.nextInt(0, range)
        number1.text = randomValue1.toString()
        number2.text = randomValue2.toString()
        result?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                resultCard.strokeColor = Color.parseColor("#FF0000")
                if ((randomValue1 - randomValue2).toString() == result?.text.toString()) {
                    val imm: InputMethodManager =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
                    result?.isFocusable = false
                    resultCard.strokeColor = Color.parseColor("#008000")
                    object : CountDownTimer(500, 1) {
                        override fun onTick(millisUntilFinished: Long) {
                        }

                        override fun onFinish() {
                            score += 1
                            val toFragment: String = level + score.toString()
                            val bundles = bundleOf("amount" to toFragment)
                            findNavController().navigate(
                                R.id.action_minus_game_2_to_minus_game_1,
                                bundles
                            )
                        }
                    }.start()
                }
            }
        })
        microImage?.setOnClickListener {
            if (flag == 0) {
                flag = 1
                microImage?.setImageResource(
                    R.drawable.micro
                )
                Toast.makeText(requireActivity(), "Say the answer", Toast.LENGTH_SHORT).show()
                startSpeechToText()
            }
        }
        return v
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
                microImage?.setImageResource(R.drawable.inactive_micro)
                flag = 0
            }

            override fun onError(i: Int) {}

            override fun onResults(bundle: Bundle) {
                val results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (results != null) {
                    val toClear: String = results[0]
                    var cleared = ""
                    var localFlag = 0
                    for (i in toClear.indices) {
                        Log.d("hueta konesho", toClear[i].toString())
                        if (toClear[i].code in 48..57 || (toClear[i].code == 45 && localFlag == 0)) {
                            localFlag = 1
                            cleared += toClear[i]
                        } else {
                            if (toClear[i] != ' ') {
                                if (cleared.isNotEmpty())
                                    break
                            }
                        }
                    }
                    if (cleared == "")
                        cleared = "-1"
                    result?.setText(cleared.toInt().toString(), TextView.BufferType.EDITABLE)
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
         * @return A new instance of fragment minus_game_2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MinusGameSecond().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
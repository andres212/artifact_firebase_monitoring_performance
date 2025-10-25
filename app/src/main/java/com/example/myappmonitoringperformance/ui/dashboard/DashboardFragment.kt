package com.example.myappmonitoringperformance.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.FirebasePerformance
import com.example.myappmonitoringperformance.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var screenDashboardTrace = FirebasePerformance.getInstance().newTrace("screen_dashboard_trace")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        screenDashboardTrace.start()

        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())

        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it

            screenDashboardTrace.incrementMetric("text_updated", 1)
        }

        val button: Button? = binding.buttonSendEvent
        dashboardViewModel.buttonText.observe(viewLifecycleOwner) {
            button?.text = it
        }
        binding.buttonSendEvent?.setOnClickListener {
            Log.d("FirebasePerformance", "Botón presionado, enviando evento")

            val buttonTrace = FirebasePerformance.getInstance().newTrace("button_send_event_trace")
            buttonTrace.start()
            buttonTrace.incrementMetric("button_clicks", 1)

            view?.postDelayed({
                buttonTrace.stop()
                Log.d("FirebasePerformance", "Traza finalizada y enviada a Firebase")
            }, 1000)

            val params = Bundle().apply {
                putString("screen", "Dashboard")
                putString("action", "button_send_event_clicked")
            }
            firebaseAnalytics.logEvent("dashboard_button_event", params)
        }
        return root
    }

    fun onSendEventClickedButton(view: View) {
        val params = Bundle().apply {
            putString("screen", "Dashboard")
            putString("action", "button_send_event_clicked_fun_apply")
        }
        firebaseAnalytics.logEvent("dashboard_button_event_config_xml", params)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        screenDashboardTrace.stop()
        _binding = null
    }
}
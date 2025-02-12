package com.survivalcoding.network_apps.conference_app_1.presentation.conferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.network_apps.R
import com.survivalcoding.network_apps.conference_app_1.data.datasource.ConferenceRemoteDataSource
import com.survivalcoding.network_apps.conference_app_1.data.network.ConferencesApi
import com.survivalcoding.network_apps.conference_app_1.data.repository.ConferenceRepositoryImpl
import com.survivalcoding.network_apps.conference_app_1.presentation.conferences.adapter.ConferenceAdapter
import com.survivalcoding.network_apps.conference_app_1.presentation.detail.DetailFragment
import com.survivalcoding.network_apps.databinding.FragmentConferencesBinding
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException

class ConferencesFragment : Fragment() {
    private var _binding: FragmentConferencesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ConferencesViewModel> {
        ConferencesViewModelFactory(
            ConferenceRepositoryImpl(
                ConferenceRemoteDataSource(ConferencesApi)
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ConferenceAdapter(onClickView = {
            parentFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container_view,
                    DetailFragment().apply {
                        this.arguments = bundleOf(CLICK to it)
                    })
                .addToBackStack(null)
                .commit()
        })
        recyclerView.adapter = adapter

        val progressBar = binding.progressBar2
        viewModel.state.observe(this) {
            progressBar.isVisible = it.isLoading
            adapter.submitList(it.conferences)
        }

        viewModel.exception.observe(this) {
            it?.let {
                when (it) {
                    is SocketException -> Toast.makeText(
                        requireContext(),
                        "Socket Excpetion",
                        Toast.LENGTH_SHORT
                    ).show()
                    is HttpException -> Toast.makeText(
                        requireContext(),
                        "Parse Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    is UnknownHostException -> Toast.makeText(
                        requireContext(),
                        "Wrong Connection",
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> Toast.makeText(
                        requireContext(),
                        "Unknown",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CLICK = "click"
    }
}
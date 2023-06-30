package com.example.jahitmenjahit.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jahitmenjahit.R
import com.example.jahitmenjahit.application.SewApplication
import com.example.jahitmenjahit.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val sewViewModel: SewViewModel by viewModels {
        SewViewModelFactory((applicationContext as SewApplication).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SewListAdapter {sew ->
            //ini list yang bisa di klik dan mendapatkan data sew jadi tidak null
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(sew)
            findNavController().navigate(action)
        }
        binding.DataRecyclerView.adapter = adapter
        binding.DataRecyclerView.layoutManager = LinearLayoutManager(context)
        sewViewModel.allSew.observe(viewLifecycleOwner) { sew ->
            sew.let {
                if (sew.isEmpty()){
                    binding.EmptyTextView.visibility = View.VISIBLE
                    binding.IlustrasionImageView.visibility = View.VISIBLE
                }else{
                    binding.EmptyTextView.visibility = View.GONE
                    binding.IlustrasionImageView.visibility = View.GONE
                }
                adapter.submitList(sew)
            }
        }

        binding.addFAB.setOnClickListener {
            //ini button tambah sew jadi null
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
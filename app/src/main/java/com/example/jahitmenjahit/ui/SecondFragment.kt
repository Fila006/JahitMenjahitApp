package com.example.jahitmenjahit.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jahitmenjahit.application.SewApplication
import com.example.jahitmenjahit.databinding.FragmentSecondBinding
import com.example.jahitmenjahit.model.Sew

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val sewViewModel: SewViewModel by viewModels {
        SewViewModelFactory((applicationContext as SewApplication).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var sew : Sew? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,
            savedInstanceState)

        sew = args.sew
        //jika kita cek sew null maka tampilan default tambah jahitan
        //jika sew tidak null tampilan sedikit berubah ada tombol hapus dan jahit
        if (sew != null){
            binding.Deletebutton.visibility = View.VISIBLE
            binding.Savebutton.text = "Ubah"
            binding.NameFragment.setText(sew?.name)
            binding.AddressFragment.setText(sew?.address)
            binding.ManyOrdesFragment.setText(sew?.manyorders)
            binding.SizeFragment.setText(sew?.size)

        }
        val name = binding.NameFragment.text
        val address = binding.AddressFragment.text
        val manyordes = binding.ManyOrdesFragment.text
        val size = binding.SizeFragment.text
        binding.Savebutton.setOnClickListener {
            //kita beri kondisi dimana jika tambah jahitan kosong tidak bisa menyimpan
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()){
                    Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (manyordes.isEmpty()){
                Toast.makeText(context, "Banyak pesanan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (size.isEmpty()){
                Toast.makeText(context, "Ukuran tidak boleh kosong", Toast.LENGTH_SHORT).show()


            }else {
                if (sew == null){
                    val sew = Sew(0, name.toString(), address.toString(), manyordes.toString(), size.toString())
                    sewViewModel.insert(sew)
                } else {
                    val sew = Sew(sew?.id!!, name.toString(), address.toString(), manyordes.toString(), size.toString())
                    sewViewModel.update(sew)
                }
                findNavController().popBackStack() //untuk dismiss halaman ini
            }
        }
        binding.Deletebutton.setOnClickListener {
            sew?.let {  sewViewModel.delete(it) }
            findNavController().popBackStack() //untuk dismiss halaman ini
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
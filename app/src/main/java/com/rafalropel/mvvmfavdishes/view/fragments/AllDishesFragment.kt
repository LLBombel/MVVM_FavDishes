package com.rafalropel.mvvmfavdishes.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.rafalropel.mvvmfavdishes.R
import com.rafalropel.mvvmfavdishes.application.FavDishApplication
import com.rafalropel.mvvmfavdishes.databinding.FragmentAllDishesBinding
import com.rafalropel.mvvmfavdishes.view.activities.AddUpdateDishActivity
import com.rafalropel.mvvmfavdishes.view.adapter.ItemDishAdapter
import com.rafalropel.mvvmfavdishes.viewmodel.FavDishViewModel
import com.rafalropel.mvvmfavdishes.viewmodel.FavDishViewModelFactory

class AllDishesFragment : Fragment() {

    private var _binding: FragmentAllDishesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentAllDishesBinding.inflate(inflater, container, false)


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rvDishesList.layoutManager = GridLayoutManager(requireActivity(), 2)
        val itemDishAdapter = ItemDishAdapter(this)
        binding.rvDishesList.adapter = itemDishAdapter
        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner) { dishes ->
            dishes.let {
                if (it.isNotEmpty()) {
                    binding.rvDishesList.visibility = View.VISIBLE
                    binding.tvNoDishes.visibility = View.GONE

                    itemDishAdapter.dishesList(it)
                } else {
                    binding.rvDishesList.visibility = View.GONE
                    binding.tvNoDishes.visibility = View.VISIBLE
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_dishes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_dish -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
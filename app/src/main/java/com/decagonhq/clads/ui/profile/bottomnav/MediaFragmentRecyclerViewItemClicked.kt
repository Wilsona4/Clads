package com.decagonhq.clads.ui.profile.bottomnav

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.HomeFragmentBinding
import com.decagonhq.clads.databinding.MediaFragmentRecyclerViewItemClickedBinding

class MediaFragmentRecyclerViewItemClicked: Fragment() {
    private var _binding: MediaFragmentRecyclerViewItemClickedBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = MediaFragmentRecyclerViewItemClickedBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.photo_gallery_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.media_share -> Toast.makeText(requireActivity(), "Share clicked", Toast.LENGTH_SHORT).show()
            R.id.media_edit -> Toast.makeText(requireActivity(), "Edit clicked", Toast.LENGTH_SHORT).show()
            R.id.media_delete -> Toast.makeText(requireActivity(), "Delete clicked", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
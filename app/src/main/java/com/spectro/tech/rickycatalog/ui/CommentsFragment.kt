package com.spectro.tech.rickycatalog.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.databinding.FragmentCommentsBinding
import com.spectro.tech.rickycatalog.model.domain.Comments
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCommentsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

    }

    private fun initListeners() {
        binding.floatbtnAdd.setOnClickListener {
            addCommentDialog()
        }
    }

    private fun getComments() {
        viewModel.getComments()
    }

    private fun addCommentDialog() {

        val customDialog =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_layout, null)

        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)

        builder.setTitle("Add new comment")
        builder.setView(customDialog)
        builder.setCancelable(false)
        builder.setPositiveButton("Add", null)
        builder.setNegativeButton("Close") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()

        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {

                val characterName =
                    customDialog.findViewById<TextInputEditText>(R.id.dialog_edt_character).text.toString()

                val comment =
                    customDialog.findViewById<TextInputEditText>(R.id.dialog_edt_comments).text.toString()

                var isValid = true

                if (characterName.isBlank() || comment.isBlank()) {
                    isValid = false
                    Toast.makeText(
                        requireContext(),
                        "Insert Character's name and your comment to continue.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                if (isValid) {

                    val addComment = Comments(
                        Random.nextInt(0, 1000),
                        characterName,
                        comment
                    )

                    viewModel.upsertComment(addComment, ::onSuccessDialog)
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

    private fun onSuccessDialog(show: Boolean, errorMessage: String) {

        val title = "Adding Comment"
        val positiveButton = "Close"

        val message = if (show) {
            "Comment added successfully!"
        } else {
            errorMessage
        }

        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)

        builder.setPositiveButton(positiveButton) { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }
}
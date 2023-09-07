package com.spectro.tech.rickycatalog.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.databinding.FragmentCommentsBinding
import com.spectro.tech.rickycatalog.epoxy.CommentListController
import com.spectro.tech.rickycatalog.model.domain.Comments
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var epoxyController: CommentListController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCommentsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEpoxyRecyclerView()
        initListeners()
        getComments()
    }

    private fun initEpoxyRecyclerView() {
        epoxyController = CommentListController(::editComments, ::deleteComments)
        binding.epoxyCharacterComments.setController(epoxyController)
    }

    private fun initListeners() {
        binding.floatbtnAdd.setOnClickListener {
            addCommentDialog()
        }
    }

    private fun getComments() {
        viewModel.getComments()

        viewModel.firestoreComments.observe(viewLifecycleOwner) {
            epoxyController.setData(it)
        }
    }

    private fun deleteComments(comments: Comments) {

        val positiveButton = "Confirm"
        val negativeButton = "Close"

        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
        builder.setTitle("Deleting Comment")
        builder.setMessage("Do you really wish to delete this comment?")
        builder.setCancelable(false)

        builder.setPositiveButton(positiveButton) { dialog, _ ->
            viewModel.deleteComment(comments, ::deleteSnackbar)
            dialog.dismiss()
        }
        builder.setNegativeButton(negativeButton) { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun editComments(comments: Comments) {

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
                        Random.nextInt(0, 1000).toString(),
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

        if (show) {
            viewModel.clearCommentsList()
            viewModel.getComments()
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

    private fun deleteSnackbar(show: Boolean, errorMessage: String) {

        val message = if (show) {
            "Comment deleted successfully!"
        } else {
            "Error while deleting comment. Error details: $errorMessage"
        }

        if (show) {
            viewModel.clearCommentsList()
            viewModel.getComments()
        }

        val snackbar = Snackbar.make(binding.fragmentComments, message, Snackbar.LENGTH_SHORT)

        snackbar.setAction("Close") {
            snackbar.dismiss()
        }

        snackbar.show()
    }
}
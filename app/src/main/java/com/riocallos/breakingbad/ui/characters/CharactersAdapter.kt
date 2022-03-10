package com.riocallos.breakingbad.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.riocallos.breakingbad.data.models.Character
import com.riocallos.breakingbad.databinding.ItemCharacterBinding

/**
 * [CharacterAdapter] adapter for displaying all characters
 */
class CharacterAdapter(
    private var items: Array<Character>,
    private val onClick: (Character, MaterialCardView) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    /**
     * [SquidPayIdViewHolder] for binding the views
     * @param binding as [CharacterBinding] the views to be binded
     * @param onClick as [Unit] callback when view is clicked
     */
    class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
        private val onClick: (Character, MaterialCardView) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * bind the [SquidPayId] to display the online bank
         */
        fun bind(character: Character){
            binding.data = character
            //binding.card.transitionName = character.char_id.toString()
            binding.container.setOnClickListener {
                onClick(character, binding.card)
            }
            binding.executePendingBindings()
        }
    }

    /**
     * Setup the binding for the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        /**
         * inflates the current layout with view holder
         */
        ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return CharacterViewHolder(it, onClick)
        }
    }

    /**
     * Binds the data to the view
     */
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * Returns the number of privacy titles
     */
    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: Array<Character>) {
        items = newItems
        notifyDataSetChanged()
    }

}
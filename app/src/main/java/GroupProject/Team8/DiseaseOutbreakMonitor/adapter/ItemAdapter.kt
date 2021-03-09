package GroupProject.Team8.DiseaseOutbreakMonitor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import GroupProject.Team8.DiseaseOutbreakMonitor.R
import GroupProject.Team8.DiseaseOutbreakMonitor.model.Symptom
import android.util.SparseBooleanArray

/*
    Sparse boolean array maps an integer to a boolean
    e.g. if only the 5th checkbox is checked -->
    5 maps to true and the rest map to false
*/
public var checkBoxStateArray = SparseBooleanArray()


class ItemAdapter(private val context: Context, private val dataset: List<Symptom>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>()
{
    //var checkBoxStateArray = SparseBooleanArray()

    class ItemViewHolder (private val view: View) : RecyclerView.ViewHolder(view)
    {
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val checkBox: CheckBox = view.findViewById(R.id.myCheckBox)

        init
        {
            checkBox.setOnClickListener {
                if (!checkBoxStateArray.get(adapterPosition, false))
                {   // box checked
                    checkBox.isChecked = true
                    checkBoxStateArray.put(adapterPosition, true)
                }
                else
                {
                    // box not checked
                    checkBox.isChecked = false
                    checkBoxStateArray.put(adapterPosition, false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, amcamviewType: Int): ItemViewHolder
    {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int
    {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        val item = dataset[position]
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)
        holder.checkBox.isChecked = checkBoxStateArray.get(position,false)
    }

    // can't call this from SymptomsActivity for some reason... So I called checkBoxStateArray directly
    //public fun getCheckBoxArray() : SparseBooleanArray
    //{
        //return checkBoxStateArray
    //}

}

package com.dicoding.hagrinoscope.artikel

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.hagrinoscope.R

class ArticleAdapter(private val onArticleClickListener: (Article) -> Unit) :
    ListAdapter<Article, ArticleAdapter.ViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = getItem(position)

        holder.titleTextView.text = currentArticle.title
        holder.contentTextView.text = currentArticle.content

        // Set OnClickListener to open the link in a browser
        holder.articleItemLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentArticle.link))
            it.context.startActivity(intent)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.articleTitleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.articleContentTextView)
        val articleItemLayout: View = itemView.findViewById(R.id.articleItemLayout)
    }

    private class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}
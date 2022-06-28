package com.ishzk.android.newspicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.*
import com.ishzk.android.newspicker.databinding.ActivityMainBinding
import com.ishzk.android.newspicker.databinding.NewsRowBinding
import com.ishzk.android.newspicker.model.Article
import com.ishzk.android.newspicker.viewmodel.MainViewModel
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val newsListAdapter: NewsListAdapter by lazy { NewsListAdapter(viewModel) }
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(NewsSearchViewListener())

        binding.newsView.adapter = newsListAdapter
        binding.newsView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.newsList.observe(this){
            binding.progressBar.visibility = ProgressBar.INVISIBLE
            newsListAdapter.submitList(it)
        }
    }

    inner class NewsSearchViewListener: SearchView.OnQueryTextListener{
        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            binding.progressBar.visibility = ProgressBar.VISIBLE
            viewModel.fetchNews(query ?: "", Date())
            return true
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private inner class NewsListAdapter(private val viewModel: MainViewModel):
        ListAdapter<Article, NewsListViewHolder>(DiffCallback){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return NewsListViewHolder(NewsRowBinding.inflate(layoutInflater, parent, false))
        }

        override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
            holder.bind(getItem(position), viewModel)
        }
    }

    private inner class NewsListViewHolder(private val binding: NewsRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Article, viewModel: MainViewModel) {
            binding.run {
                binding.article = item
                executePendingBindings()
            }
        }
    }
}

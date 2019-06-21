package com.coldwizards.demoapp.instagram

import android.content.Context
import android.os.Handler
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coldwizards.demoapp.R
import com.coldwizards.demoapp.databinding.PostVerticalItemBinding
import com.coldwizards.demoapp.model.Comment
import com.coldwizards.demoapp.model.Post
import com.coldwizards.demoapp.model.User
import com.coldwizards.demoapp.utils.loadImage

/**
 * Created by jess on 19-6-13.
 */
class PostAdapter : PagedListAdapter<Post, PostAdapter.PostViewHolder>(diffCallback) {

    /**
     * 添加评论layout的点击的监听事件，返回当前item的position，offset是recyclerview需要滚动的offset
     */
    var commentLayoutListener: (positon: Int, offset: Int) -> Unit = { positon, offset -> }
    var likeListener: (post: Post, isChecked: Boolean) -> Unit = { post, isChecked -> }

    var mUser: User? = null
        get() = field
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostVerticalItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PostViewHolder(parent.context, binding)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(getItem(position), position, commentLayoutListener)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem
        }
    }

    fun setCommentListener(listener: (position: Int, offset: Int) -> Unit) {
        commentLayoutListener = listener
    }

    fun likeListener(listener: (post: Post, isChecked: Boolean) -> Unit) {
        likeListener = listener
    }


    /**
     * 内部类容易memory leak
     */
    inner class PostViewHolder(
        val context: Context,
        val binding: PostVerticalItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val animView = itemView.findViewById<ImageView>(R.id.anim_like)
        val likeIcon = itemView.findViewById<CheckBox>(R.id.like)
        val likeCount = itemView.findViewById<TextView>(R.id.likeCount)
        val imageView = itemView.findViewById<ImageView>(R.id.pictures)
        val addComment = itemView.findViewById<TextView>(R.id.addComment)
        val postTime = itemView.findViewById<TextView>(R.id.postTime)
        val content = itemView.findViewById<TextView>(R.id.content)
        val commentCount = itemView.findViewById<TextView>(R.id.commentCount)
        val avatorComment = itemView.findViewById<ImageView>(R.id.avatorComment)
        val commentsLayout = itemView.findViewById<LinearLayout>(R.id.comments_layout)

        var post: Post? = null

        fun bindView(post: Post?, position: Int, commentListener: (position: Int, offset: Int) -> Unit) {
            this.post = post

            binding.post = post!!

            commentCount.text = "共${post.comments?.size}条评论"
            content.text = Html.fromHtml(
                "<b>${post.author.username}</b>&nbsp; ${post.content}"
            )
            mUser?.let {user ->
                if (post?.likeUsers?.contains(user.username)) likeIcon.isChecked = true else likeIcon.isChecked = false
            }

            if (post.pictures?.isEmpty()!!) {
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.loadImage(context, ContextCompat.getDrawable(context, R.drawable.image_holder)!!)
            } else {
                imageView.loadImage(context, post?.pictures?.get(0)!!)
            }

            /**
             * 点击ImageView时，判断likeIcon是否checked状态，如果不是，执行动画效果
             * 且[post]的likeCount加一
             */
            imageView.setOnClickListener {
                if (!likeIcon.isChecked) {
                    val animation = AnimationUtils.loadAnimation(
                        context,
                        R.anim.like_animation
                    )

                    animView.startAnimation(animation)
                    likeIcon.isChecked = !likeIcon.isChecked
                }
            }

            /**
             * 点击赞的监听事件
             */
            likeIcon.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    post?.likeCount = post?.likeCount?.plus(1)!!
                    mUser?.let {
                        post?.likeUsers?.add(it.username)
                    }
                } else {
                    post?.likeCount = post?.likeCount?.minus(1)!!
                    mUser?.let {
                        post?.likeUsers?.remove(it.username)
                    }
                }

                likeCount.text = "${post?.likeCount}次赞"

                // 延时等爱心动画完成再回调
                Handler().postDelayed({
                    likeListener(post, isChecked)
                }, 1500)
            }

            //点击添加评论时的事件
            addComment.setOnClickListener {
                commentListener(position, postTime.bottom)
            }

            commentsLayout.removeAllViews()
            post?.comments?.forEach {
                Log.d("评论", it.content)
                commentsLayout.addView(newCommentTextView(it))
            }
        }

        private fun newCommentTextView(comment: Comment): TextView {
            val textview = TextView(context)
            textview.setText(
                Html.fromHtml(
                    "<b>${comment.user.username}</b>&nbsp; ${comment.content}"
                )
            )
            textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textview.setTextColor(ContextCompat.getColor(context, R.color.black))
            return textview
        }
    }
}
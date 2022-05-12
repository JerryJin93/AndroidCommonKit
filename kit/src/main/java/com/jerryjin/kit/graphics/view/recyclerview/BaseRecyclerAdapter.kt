package com.jerryjin.kit.graphics.view.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.core.view.contains
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.min

/**
 * Author: Jerry
 *
 * Created at: 2021/12/08 9:31
 *
 * GitHub: https://github.com/JerryJin93
 *
 * Blog:
 *
 * Email: jerry93@foxmail.com
 *
 * WeChat: AcornLake
 *
 * Version: 1.0.0
 *
 * Description: Base adapter for [androidx.recyclerview.widget.RecyclerView].
 */
typealias OnClickListener<DATA, VH> = (BaseRecyclerAdapter<DATA, VH>, View, Int) -> Unit
typealias OnLongClickListener<DATA, VH> = (BaseRecyclerAdapter<DATA, VH>, View, Int) -> Boolean

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseRecyclerAdapter<DATA : IDiffCallback<DATA>, VH : BaseVH<DATA>> :
    RecyclerView.Adapter<BaseVH<DATA>>() {

    companion object {

        private const val LOADING_FADE_DURATION: Long = 400

        const val TYPE_LOADING_VIEW = -4
        const val TYPE_HEADER_VIEW = -3
        const val TYPE_EMPTY_VIEW = -2
        const val TYPE_FOOTER_VIEW = -1
    }

    /**
     * Loading orientation.
     *
     * Notice: the direction you scroll toward is opposite to the visual effect the view displays.
     */
    enum class LoadingOrientation {
        /**
         * Two scenarios in total:
         *
         * 1. The orientation of the layout manager is horizontal, and you scroll rightwards.
         *
         * 2. The orientation of the layout manager is vertical, and you scroll downwards.
         */
        Up,

        /**
         * Two scenarios also:
         *
         * 1. The orientation of the layout manager is horizontal, and you scroll leftwards.
         *
         * 2. The orientation of the layout manager is vertical, and you scroll upwards.
         */
        Down,
    }

    private val onChildClickIds = LinkedHashSet<Int>()
    private val onChildLongClickIds = LinkedHashSet<Int>()

    private var mData = mutableListOf<DATA>()
    val data: List<DATA>
        get() = mData

    protected lateinit var mHost: RecyclerView
    private lateinit var velocityTracker: VelocityTracker

    private val diffCallback = DiffCallback<DATA>()

    private lateinit var headerLayout: FrameLayout
    private lateinit var emptyLayout: FrameLayout
    private lateinit var footerLayout: FrameLayout
    private lateinit var loadingLayout: FrameLayout

    var onItemClickListener: OnClickListener<DATA, VH>? = null
    var onItemLongClickListener: OnLongClickListener<DATA, VH>? = null
    var onItemChildClickListener: OnClickListener<DATA, VH>? = null
    var onItemChildLongClickListener: OnLongClickListener<DATA, VH>? = null

    private val emptyCount: Int
        get() = if (hasEmptyLayout()) 1 else 0

    private val headerCount: Int
        get() = if (hasHeaderLayout()) 1 else 0

    private val footerCount: Int
        get() = if (hasFooterLayout()) 1 else 0

    private val loadingCount: Int
        get() = if (hasLoadingLayout()) if (hideLoading) 0 else 1 else 0

    private var hideLoading = true

    private var loadingOrientation = LoadingOrientation.Down
        set(value) {
            if (value != field)
                field = value
        }

    var onLoadMoreListener: (LoadingOrientation) -> Unit = {}

    private val finishLoadingAnim by lazy {
        AlphaAnimation(1f, 0f).apply {
            duration = LOADING_FADE_DURATION
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) = Unit

                override fun onAnimationEnd(animation: Animation?) = hideLoading(true)

                override fun onAnimationRepeat(animation: Animation?) = Unit
            })
        }
    }

    sealed class DataChangedType {
        object Add : DataChangedType()
        object Remove : DataChangedType()
        object Replace : DataChangedType()
    }

    protected open val onDataChangedObserver: (DataChangedType, DATA?, DATA?) -> Unit =
        { _, _, _ -> }

    private val _onDataRangeChangedObserver: (DataChangedType, oldDataList: List<DATA>?, newDataList: List<DATA>?) -> Unit =
        { dataChangedType, oldDataList, newDataList ->
            if (hasLoadingLayout() && loadingLayout.visibility != View.GONE)
                loadingLayout.startAnimation(finishLoadingAnim)
            when (dataChangedType) {
                DataChangedType.Replace -> {
                    for (pos in 0 until min(oldDataList?.size ?: 0, newDataList?.size ?: 0)) {
                        onDataChangedObserver(DataChangedType.Remove, oldDataList?.get(pos), null)
                        onDataChangedObserver(DataChangedType.Add, null, newDataList?.get(pos))
                    }
                }
                DataChangedType.Add -> {
                    newDataList?.run {
                        for (pos in indices) {
                            onDataChangedObserver(DataChangedType.Add, null, this[pos])
                        }
                    }
                }
                DataChangedType.Remove -> {
                    oldDataList?.run {
                        for (pos in indices) {
                            onDataChangedObserver(DataChangedType.Remove, this[pos], null)
                        }
                    }
                }
            }
        }

    protected open val onDataRangeChangedObserver: (DataChangedType, IntRange, List<DATA>) -> Unit =
        { _, _, _ -> }

    fun addData(newData: DATA) {
        mData.add(newData)
        notifyItemInserted(itemCount - 1)
        onDataChangedObserver(DataChangedType.Add, null, newData)
    }

    fun addData(position: Int, newData: DATA) {
        mData.add(position, newData)
        notifyItemInserted(position)
        onDataChangedObserver(DataChangedType.Add, null, newData)
    }

    fun addData(newData: List<DATA>) {
        val oldData = mData
        mData.addAll(newData)
        notifyItemRangeInserted(mData.size - newData.size, newData.size)
        _onDataRangeChangedObserver(DataChangedType.Add, oldData, mData)
    }

    fun addData(position: Int, newData: List<DATA>) {
        val oldData = mData
        mData.addAll(position, newData)
        notifyItemRangeInserted(0, newData.size)
        _onDataRangeChangedObserver(DataChangedType.Add, oldData, mData)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun removeAt(index: Int) {
        val removed = mData.removeAt(index)
        notifyItemRemoved(index)
        onDataChangedObserver(DataChangedType.Remove, removed, null)
    }

    fun remove(toBeRemoved: DATA) = mData.indexOf(toBeRemoved).run {
        if (this != -1) removeAt(this)
    }

    fun setData(newData: List<DATA>) {
        val result = DiffUtil.calculateDiff(diffCallback.apply {
            oldItems = mData
            newItems = newData
        })
        val oldList = mData
        mData = newData as MutableList<DATA>
        result.dispatchUpdatesTo(this)
        _onDataRangeChangedObserver(DataChangedType.Replace, oldList, mData)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        velocityTracker = VelocityTracker.obtain()
        mHost = recyclerView
        recyclerView.run {
            setOnTouchListener { _, event ->
                velocityTracker.run {
                    addMovement(event)
                    computeCurrentVelocity(1)
                }
                false
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                private val intPair = object {

                    var first: Int = 0
                    var second: Int = 0

                    operator fun component1() = first
                    operator fun component2() = second

                    fun of(first: Int, second: Int) = apply {
                        this.first = first
                        this.second = second
                    }
                }

                private infix fun Int.pairsWith(another: Int) = intPair.of(this, another)

                private var horizontallyScroll = false

                /**
                 * True if the scroll towards end, false otherwise, namely start.
                 */
                private var scrollTowardsEnd = false

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val xVelocity = velocityTracker.xVelocity
                    val yVelocity = velocityTracker.yVelocity
                    horizontallyScroll = abs(xVelocity) > abs(yVelocity)
                    scrollTowardsEnd = if (horizontallyScroll) xVelocity < 0 else yVelocity < 0

                    val layoutManager = recyclerView.layoutManager ?: return

                    @Suppress("LocalVariableName")
                    val INVALID_ORIENTATION = -1
                    val orientation = when (layoutManager) {
                        is LinearLayoutManager -> layoutManager.orientation
                        // which is same as LinearLayoutManager in that GridLayoutManager extends it.
                        is GridLayoutManager -> layoutManager.orientation
                        else -> INVALID_ORIENTATION
                    }
                    val f2l = when (layoutManager) {
                        is LinearLayoutManager -> layoutManager.run {
                            findFirstCompletelyVisibleItemPosition() pairsWith findLastCompletelyVisibleItemPosition()
                        }
                        is GridLayoutManager -> layoutManager.run {
                            findFirstCompletelyVisibleItemPosition() pairsWith findLastCompletelyVisibleItemPosition()
                        }
                        is BaseLayoutManager -> layoutManager.run {
                            findFirstCompletelyVisibleItemPosition() pairsWith findLastCompletelyVisibleItemPosition()
                        }
                        else -> BaseLayoutManager.INVALID_POSITION pairsWith BaseLayoutManager.INVALID_POSITION
                    }

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val checkOrientation =
                            orientation == LinearLayoutManager.HORIZONTAL && horizontallyScroll || orientation == LinearLayoutManager.VERTICAL && !horizontallyScroll

                        val (firstCompletelyVisibleItemPosition, lastCompletelyVisibleItemPosition) = f2l
                        if (firstCompletelyVisibleItemPosition == 0 && !scrollTowardsEnd) {
                            if (loadingOrientation == LoadingOrientation.Up && checkOrientation) {
                                hideLoading(false)
                                onLoadMoreListener(LoadingOrientation.Up)
                            }
                        } else if (lastCompletelyVisibleItemPosition == layoutManager.itemCount - 1 && scrollTowardsEnd) {
                            if (loadingOrientation == LoadingOrientation.Down && checkOrientation) {
                                hideLoading(false)
                                onLoadMoreListener(LoadingOrientation.Down)
                            }
                        }
                    }
                }
            })
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        velocityTracker.recycle()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun hideLoading(hide: Boolean) {
        if (!::loadingLayout.isInitialized) return
        loadingLayout.visibility = if (!hide) View.VISIBLE else View.GONE
        hideLoading = hide
        notifyDataSetChanged()
    }

    protected fun isHostInitialized() = ::mHost.isInitialized

    fun withInitializedHost(block: RecyclerView.() -> Unit) =
        mHost.takeIf { isHostInitialized() }?.run(block)

    private fun hasHeaderLayout() = ::headerLayout.isInitialized && headerLayout.childCount > 0

    private fun hasFooterLayout() = ::footerLayout.isInitialized && footerLayout.childCount > 0

    private fun hasEmptyLayout() =
        ::emptyLayout.isInitialized && emptyLayout.childCount > 0 && data.isEmpty()

    private fun hasLoadingLayout() =
        ::loadingLayout.isInitialized && loadingLayout.childCount > 0 && !hideLoading

    final override fun getItemViewType(position: Int): Int {
        val hasHeader = hasHeaderLayout()
        val hasFooter = hasFooterLayout()
        val hasLoading = hasLoadingLayout()
        if (hasEmptyLayout()) {
            return when (position) {
                0 -> if (hasHeader) TYPE_HEADER_VIEW else TYPE_EMPTY_VIEW
                1 -> if (hasHeader) TYPE_EMPTY_VIEW else if (hasFooter) TYPE_FOOTER_VIEW else TYPE_EMPTY_VIEW
                2 -> if (hasFooter) TYPE_FOOTER_VIEW else TYPE_EMPTY_VIEW
                else -> TYPE_EMPTY_VIEW
            }
        }
        return if (hasHeader && position == 0) TYPE_HEADER_VIEW
        else if (!hasHeader && hasLoading && position == 0 && loadingOrientation == LoadingOrientation.Up) TYPE_LOADING_VIEW
        else if (hasHeader && hasLoading && position == 1 && loadingOrientation == LoadingOrientation.Up) TYPE_LOADING_VIEW
        else {
            val adjustPos =
                (if (hasHeader) position - 1 else position) - if (hasLoading && loadingOrientation == LoadingOrientation.Up) 1 else 0
            val dataSize = data.size
            if (adjustPos < dataSize)
                getDataItemViewType(adjustPos)
            else {
                if (hasFooterLayout()) TYPE_FOOTER_VIEW
                else getDataItemViewType(adjustPos)
            }
        }
    }

    protected open fun getDataItemViewType(position: Int) = super.getItemViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<DATA> =
        when (viewType) {
            TYPE_EMPTY_VIEW -> DefBaseViewHolder(emptyLayout)
            TYPE_HEADER_VIEW -> DefBaseViewHolder(headerLayout)
            TYPE_FOOTER_VIEW -> DefBaseViewHolder(footerLayout)
            TYPE_LOADING_VIEW -> DefBaseViewHolder(loadingLayout)
            else -> createVH(parent, viewType).apply {
                onPreBindClickListener()
                bindClickListener(this)
            }
        }.apply {
            onViewHolderCreated(this, viewType)
        }

    override fun onBindViewHolder(holder: BaseVH<DATA>, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == TYPE_HEADER_VIEW || itemViewType == TYPE_EMPTY_VIEW || itemViewType == TYPE_LOADING_VIEW || itemViewType == TYPE_FOOTER_VIEW)
            return
        val pos = position - headerCount - when (loadingOrientation) {
            LoadingOrientation.Up -> loadingCount
            LoadingOrientation.Down -> 0
        }
        holder.onBind(this, mData[pos], position)
    }

    override fun getItemCount(): Int = if (hasEmptyLayout()) {
        var itemCount = 1
        if (hasLoadingLayout()) itemCount++
        if (hasHeaderLayout()) itemCount++
        if (hasFooterLayout()) itemCount++
        itemCount
    } else emptyCount + loadingCount + headerCount + data.size + footerCount

    protected abstract fun createVH(parent: ViewGroup, viewType: Int): VH
    protected open fun onViewHolderCreated(holder: BaseVH<DATA>, viewType: Int) {}

    protected open fun onPreBindClickListener() {}

    private fun bindClickListener(holder: BaseVH<DATA>) {
        fun View.enableClick() {
            if (!isClickable) isClickable = true
        }

        fun View.enableLongClick() {
            if (!isLongClickable) isLongClickable = true
        }

        onItemClickListener?.run {
            holder.itemView.setOnClickListener {
                val position = holder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnClickListener
                this(this@BaseRecyclerAdapter, it, position - headerCount - loadingCount)
            }
        }

        onItemChildClickListener?.run {
            onChildClickIds.forEach { id ->
                val view = holder.itemView.findViewById<View>(id)?.apply { enableClick() }
                view?.setOnClickListener {
                    val position = holder.bindingAdapterPosition
                    if (position == RecyclerView.NO_POSITION) return@setOnClickListener
                    this(this@BaseRecyclerAdapter, it, position - headerCount - loadingCount)
                }
            }
        }

        onItemLongClickListener?.run {
            holder.itemView.setOnLongClickListener { child ->
                val position = holder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnLongClickListener false
                this(this@BaseRecyclerAdapter, child, position - headerCount - loadingCount)
                    ?: false
            }
        }

        onItemChildLongClickListener?.run {
            onChildLongClickIds.forEach { id ->
                val view = holder.itemView.findViewById<View>(id)?.apply { enableLongClick() }
                view?.setOnLongClickListener { child ->
                    val position = holder.bindingAdapterPosition
                    if (position == RecyclerView.NO_POSITION) return@setOnLongClickListener false
                    this(this@BaseRecyclerAdapter, child, position - headerCount - loadingCount)
                        ?: false
                }
            }
        }
    }

    protected open fun addOnChildClickIds(vararg id: Int) {
        onChildClickIds.addAll(id.toList())
    }

    protected open fun addOnChildLongClickIds(vararg id: Int) {
        onChildLongClickIds.addAll(id.toList())
    }

    private fun generateLayoutParamsForFunctionalView(
        width: Int = MATCH_PARENT,
        height: Int = WRAP_CONTENT
    ) =
        RecyclerView.LayoutParams(width, height)

    @SuppressLint("NotifyDataSetChanged")
    fun setEmptyView(view: View) {
        if (!::emptyLayout.isInitialized)
            emptyLayout = FrameLayout(view.context).apply {
                layoutParams = generateLayoutParamsForFunctionalView(height = MATCH_PARENT)
            }
        emptyLayout.removeAllViews()
        emptyLayout.addView(view)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setLoadingView(view: View, orientation: LoadingOrientation) {
        loadingOrientation = orientation
        if (!::loadingLayout.isInitialized) {
            loadingLayout = FrameLayout(view.context).apply {
                layoutParams = generateLayoutParamsForFunctionalView()
            }
        }
        loadingLayout.removeAllViews()
        loadingLayout.addView(view)
        loadingLayout.visibility = View.GONE
        notifyDataSetChanged()
    }

    fun setHeaderView(view: View) {
        if (!::headerLayout.isInitialized)
            headerLayout = FrameLayout(view.context).apply {
                layoutParams = generateLayoutParamsForFunctionalView()
            }
        headerLayout.removeAllViews()
        headerLayout.addView(view)
        notifyItemInserted(0)
    }

    fun addHeaderView(view: View, layoutParams: FrameLayout.LayoutParams, index: Int = -1) =
        if (hasHeaderLayout()) headerLayout.addView(view, index, layoutParams)
        else setHeaderView(view)

    fun setFooterView(view: View) {
        if (!::footerLayout.isInitialized)
            footerLayout = FrameLayout(view.context).apply {
                layoutParams = generateLayoutParamsForFunctionalView()
            }
        footerLayout.removeAllViews()
        footerLayout.addView(view)
        notifyItemInserted(itemCount - 1)
    }

    fun addFooterView(view: View, layoutParams: FrameLayout.LayoutParams, index: Int = -1) =
        if (hasFooterLayout()) footerLayout.addView(view, index, layoutParams)
        else setFooterView(view)


    fun removeHeaders() = if (::headerLayout.isInitialized) {
        headerLayout.removeAllViews()
        true
    } else false

    fun removeEmptyView() = if (::emptyLayout.isInitialized) {
        emptyLayout.removeAllViews()
        true
    } else false

    fun removeFooters() = if (::footerLayout.isInitialized) {
        footerLayout.removeAllViews()
        true
    } else false

    fun removeLoading() = if (::loadingLayout.isInitialized) {
        loadingLayout.removeAllViews()
        true
    } else false

    fun finishLoading() {
        if (hasLoadingLayout()) loadingLayout.visibility = View.GONE
    }
}

abstract class BaseVH<DATA : IDiffCallback<DATA>>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    abstract fun <VH : BaseVH<DATA>> onBind(
        adapter: BaseRecyclerAdapter<DATA, VH>,
        data: DATA,
        position: Int
    )
}

private class DefBaseViewHolder<DATA : IDiffCallback<DATA>>(itemView: View) :
    BaseVH<DATA>(itemView) {
    override fun <VH : BaseVH<DATA>> onBind(
        adapter: BaseRecyclerAdapter<DATA, VH>,
        data: DATA,
        position: Int
    ) {
    }
}

abstract class BaseDataBindingVH<DATA : IDiffCallback<DATA>, DB : ViewDataBinding>(itemView: View) :
    BaseVH<DATA>(itemView) {
    protected val mBinding: DB? = DataBindingUtil.bind(itemView)
}

interface StatefulView {

    fun onStateChange(old: State, new: State)
    fun show()
    fun hide()

    enum class State {
        UNDEFINED,
        LOADING,
        EMPTY,
        ERROR,
    }
}

interface StatefulMediator {
    fun showLoading()
    fun showContent()
    fun showEmpty()
    fun showError()
    fun onDetach()
}

@Suppress("unused")
class StatefulViewMediator private constructor(
    private var mCompanion: View?,
    private val stateful: StatefulView,
) : StatefulMediator {

    private var mWrapper: FrameLayout? = null
    private var mState = StatefulView.State.UNDEFINED

    init {
        mCompanion?.run {
            mWrapper = FrameLayout(parentContext())
            val parent = parent as ViewGroup

            detachFromParent()
            parent.addView(mWrapper, layoutParams)
            mWrapper!!.addView(this)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(view: View, stateful: StatefulView): StatefulMediator =
            StatefulViewMediator(view, stateful)
    }


    private fun showStatefulView() {
        mCompanion?.visibility = View.GONE
        stateful.show()
    }

    private fun hideStatefulView() {
        mCompanion?.visibility = View.VISIBLE
        stateful.hide()
    }

    override fun showLoading() = updateState(StatefulView.State.LOADING)

    override fun showContent() = hideStatefulView()

    override fun showEmpty() = updateState(StatefulView.State.EMPTY)

    override fun showError() = updateState(StatefulView.State.ERROR)

    override fun onDetach() {
        mWrapper!!.run {
            removeView(mCompanion)
            (parent as ViewGroup).run {
                removeView(mWrapper)
                addView(mCompanion, mWrapper!!.layoutParams)
            }
        }
        mCompanion = null
        mWrapper = null
    }

    private fun updateState(newState: StatefulView.State) {
        stateful.onStateChange(mState, newState)
        mState = newState
        showStatefulView()
    }

}

fun View.parentContext(): Context = (parent as ViewGroup).context
fun View.detachFromParent() = (parent as? ViewGroup)?.run {
    if (contains(this)) removeView(this)
    true
} ?: false
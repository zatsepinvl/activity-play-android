package com.zatsepinvl.activityplay.intro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.zatsepinvl.activityplay.R
import com.zatsepinvl.activityplay.android.color
import com.zatsepinvl.activityplay.android.fragment.navigate
import com.zatsepinvl.activityplay.color.ColoredView
import com.zatsepinvl.activityplay.databinding.FragmentIntroBinding
import com.zatsepinvl.activityplay.intro.fragment.IntroFragmentDirections.Companion.mainMenu
import com.zatsepinvl.activityplay.intro.model.IntroSlideModel

data class SlideResources(
    val title: Int,
    val description: Int,
    val drawable: Int,
    val color: Int,
    val last: Boolean = false
)

val slideWelcome = SlideResources(
    title = R.string.slide_welcome_title,
    description = R.string.slide_welcome_description,
    drawable = R.drawable.play_button,
    color = R.color.colorPrimary
)

val slideParticipants = SlideResources(
    title = R.string.slide_participants_title,
    description = R.string.slide_participants_description,
    drawable = R.drawable.friendship,
    color = R.color.md_green_700
)

val slideTeams = SlideResources(
    title = R.string.slide_teams_title,
    description = R.string.slide_teams_description,
    drawable = R.drawable.choices,
    color = R.color.md_red_700
)

val slideGame = SlideResources(
    title = R.string.slide_game_title,
    description = R.string.slide_game_description,
    drawable = R.drawable.word,
    color = R.color.md_blue_700
)

val slideEnjoy = SlideResources(
    title = R.string.slide_enjoy_title,
    description = R.string.slide_enjoy_description,
    drawable = R.drawable.dance,
    color = R.color.md_purple_700
)


class IntroFragment : Fragment() {
    private lateinit var pageAdapter: IntroPageAdapter
    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageAdapter = IntroPageAdapter(parentFragmentManager)
        addSlide(slideWelcome)
        addSlide(slideParticipants)
        addSlide(slideTeams)
        addSlide(slideGame)
        addSlide(slideEnjoy)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater)
        binding.introViewPager.adapter = pageAdapter
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigate(mainMenu())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.introFinishButton.visibility = GONE
        changeCurrentColor(0)
        binding.introViewPager.addOnPageChangeListener(
            object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    changeCurrentColor(position)
                    if (position == pageAdapter.count - 1) {
                        binding.introFinishButton.visibility = VISIBLE
                        binding.introNextButton.visibility = GONE
                    } else {
                        binding.introFinishButton.visibility = GONE
                        binding.introNextButton.visibility = VISIBLE
                    }
                }
            }
        )
        binding.introNextButton.setOnClickListener {
            binding.introViewPager.arrowScroll(FOCUS_RIGHT)
        }
        binding.introFinishButton.setOnClickListener {
            navigate(mainMenu())
        }
    }

    private fun changeCurrentColor(position: Int) {
        val color = pageAdapter.getSlide(position).color
        (requireActivity() as ColoredView).changeBarsColor(color)
        binding.introContainer.setBackgroundColor(color)
    }

    private fun addSlide(resources: SlideResources) {
        val context = requireContext()
        val slide = IntroSlideModel(
            title = context.getString(resources.title),
            color = context.color(resources.color),
            description = context.getString(resources.description),
            drawable =  AppCompatResources.getDrawable(context, resources.drawable)!!
        )
        pageAdapter.addItem(slide)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class IntroPageAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val slides: MutableList<IntroSlideModel> = mutableListOf()

    fun addItem(slide: IntroSlideModel) {
        slides.add(slide)
    }

    fun getSlide(position: Int): IntroSlideModel {
        return slides[position]
    }

    override fun getItem(position: Int): Fragment {
        return IntroSlideFragment(getSlide(position))
    }

    override fun getCount(): Int {
        return slides.size
    }
}
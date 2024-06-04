package com.car.carapp.ui.fragments

import android.os.Bundle
import android.text.SpannableString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.car.carapp.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private val spannableString = SpannableString("מי אנחנו: חברת ל.ד אוטו אקוויפמנט 1990 בע\"מ\n" +
            "משווקת המותג \"קוברה\", הינה חברה מובילה\n" +
            "בתחום אביזרים מתקדמים וטכנולוגיים בתחום\n" +
            "הרכב הפועלת למעלה משלושה עשורים ומובילה\n" +
            "את הענף. תחומי פעילות החברה הינם: ייצור, שיווק\n" +
            "והתקנה של מערכות מיגון, חיישני חניה, מולטימדיה,\n" +
            "ניווט ובטיחות לרכב. פעילות החברה מתמקדת\n" +
            "במגוון רחב של מוצרים בעלי חדשנות טכנולוגית\n" +
            "המהווים את חוד החנית בתעשיית הרכב. החברה\n" +
            "חרטה על דגלה את עקרון איכות המוצרים, הפצה\n" +
            "של הטכנולוגיות המתקדמות ביותר בעולם בתחום\n" +
            "הרכב, מערכות ניווט מתקדמות עם התממשקות\n" +
            "למסכי הרכב, מערכות בידור ותקשורת, אביזרי\n" +
            "בטיחות כנגד תאונות דרכים, חיישני חניה\n" +
            "מתקדמים, מצלמות עזר קדמיות ואחוריות לחניה,\n" +
            "מערכות מתקדמות למיגון הרכב כנגד גניבה, מוצרי\n" +
            "עיצוב יוקרתיים משלימים למערכות התקשורת\n" +
            "ועוד. החברה מתמחה בין השאר בהתקנות\n" +
            "מורכבות בכל סוגי הרכב כולל רכבי יוקרה. החברה\n" +
            "פועלת בין היתר כמתקינת הבית של יבואני רכב\n" +
            "וחברות ליסינג הגדולות")
    private lateinit var mBinding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAboutBinding.inflate(inflater)

        inIt()

        return mBinding.root
    }

    private fun inIt() {

        mBinding.tvAbout.text = spannableString

        mBinding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
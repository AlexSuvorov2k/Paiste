package ru.alexsuvorov.paistewiki.tools

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.db.AppDatabase
import ru.alexsuvorov.paistewiki.model.Month
import ru.alexsuvorov.paistewiki.model.News
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.regex.Pattern

class NewsLoader : AsyncTask<Any?, Void?, Boolean?>() {
    private var monthIndex: String? = null
    private var yearIndex: String? = null

    override fun doInBackground(params: Array<Any?>): Boolean {
        val URL = params[0] as String?
        val context = params[1] as Context
        val db: AppDatabase = AppDatabase.Companion.getDatabase(context)
        val newsDao = db.newsDao()!!
        val monthDao = db.monthDao()!!
        try {
            val month = Jsoup.connect(URL).get()
            if (month != null) {
                //Список месяцев
                val monthRows = month.getElementsByClass("contlefta").select("tr")
                if (monthRows.size > 1) {
                    for (i in monthRows.size downTo 1) {
                        val monthRowElement = monthRows.get(i - 1) //check all tr tags                               i-1
                        val monthRowItems = monthRowElement.select("td") //All(1), Prod, Artist
                        val monthTitleElement = monthRowItems.first() //Select All
                        val monthLinks = monthRowItems.select("a[href]")
                        val monthLink = monthLinks.first()
                        val monthUrl = "http://paiste.com/e/news.php" + monthLink.attr("href")
                        val monthTitle = monthTitleElement.text()
                        val pYear = Pattern.compile("year=[0-9]{4}")
                        val mYear = pYear.matcher(monthUrl)
                        if (mYear.find()) {
                            val findY_Index1 = mYear.group(0)
                            val parts1 = findY_Index1!!.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            yearIndex = parts1[1]
                        }
                        val pMonth = Pattern.compile("month=\\d{1,12}")
                        val mMonth = pMonth.matcher(monthUrl)
                        if (mMonth.find()) {
                            val findM_Index1 = mMonth.group(0)
                            if (clearValues(findM_Index1!!).length == 1) {
                                val str = "0"
                                monthIndex = str + clearValues(findM_Index1) //08
                            } else {
                                monthIndex = clearValues(findM_Index1) //11
                            }
                        }
                        val mIndex = (yearIndex + monthIndex).toInt()
                        val posts = Jsoup.connect(monthUrl).get()
                        if (posts != null) {
                            val postsRows = posts.getElementsByClass("contrighta").select("tr")
                            /*FOR IMAGES
                            http://stackoverflow.com/questions/10457415/extract-image-src-using-jsoup
                            */
                            //Если есть новости
                            if (postsRows.size > 1) {
                                //начинать с первой новости, с 0 + пустая строка
                                for (j in postsRows.indices) {
                                    val postsRowElement = postsRows.get(j)
                                    val postsRowItems = postsRowElement.select("td")
                                    val postsLinks = postsRowItems.select("a[href]")
                                    for (postLink in postsLinks) {
                                        val linkUrl = "http://paiste.com/e/news.php" + postLink.attr("href")
                                        val linkTitle = postLink.text()
                                        val linkCategory = postsRowItems.get(2).text()
                                        if (newsDao.insert(News(0, linkTitle, linkCategory, linkUrl, mIndex.toLong())) > 0) {
                                            App.newsUpdated = true
                                        }
                                    }
                                }
                            }
                            monthDao.insert(Month((monthDao.lastMonthId + 1).toLong(), monthTitle, monthUrl, mIndex))
                        }
                    }
                }
            }
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
            App.errorCode = 1
            return true
        } catch (exception: UnknownHostException) {
            exception.printStackTrace()
            App.errorCode = 2
            Log.d(javaClass.simpleName, "Server error")
            return true
        } catch (exception: IOException) {
            exception.printStackTrace()
            App.errorCode = 3
            return true
        }
        App.errorCode = 0
        return true
    }

    private fun clearValues(input: String): String {
        val parts = input.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return parts[1]
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        Log.d(javaClass.simpleName, "Result is: " + result)
    }
}
package com.dicoding.hagrinoscope.artikel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.hagrinoscope.R

class Article_Fragment : Fragment() {

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var searchEditText: EditText

    // Dummy data to be used as a fallback before fetching actual data
    private val dummyData = getDummyArticleData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_, container, false)

        // Memperbarui kode untuk mendapatkan referensi dari EditText dan RecyclerView yang benar
        searchEditText = view.findViewById(R.id.editTextSearch)
        articleRecyclerView = view.findViewById(R.id.articleRecyclerView)

        // Inisialisasi adapter dengan data dummy
        articleAdapter = ArticleAdapter { article ->
            // Handle the click event for the article, e.g., open a new activity or fragment
            // You can customize this part based on your application logic
            // For now, let's just print the article title to the log
            println("Article Clicked: ${article.link}")
        }

        // Setup RecyclerView
        articleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        articleRecyclerView.adapter = articleAdapter

        // Setup listener untuk kotak pencarian
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }

            override fun afterTextChanged(editable: Editable?) {
                filterArticles(editable.toString())
            }
        })

        // Fetch the actual article data and update the adapter
        val actualArticleData = getActualArticleData()
        articleAdapter.submitList(actualArticleData)

        return view
    }

    // Function to get actual article data
    private fun getActualArticleData(): List<Article> {
        return listOf(
            Article(
                "Pohon Pisang",
                """
                    Pohon pisang termasuk adaptif terhadap segala tempat, ia bisa tumbuh subur di daerah dingin maupun panas. Pohon pisang juga termasuk yang mudah dalam perawatannya, sehingga biaya budidaya pisang tergolong murah dibandingkan dengan jenis pohon buah yang lainnya.
                    Sama seperti penanaman bibit pada umumnya, sebaiknya penanaman dilakukan pada saat hujan sudah turun atau menjelang musim hujan. Lakukan penanaman pada sore hari agar bibit tidak strees terhadap lingkungan baru. Masukkan bibit ke dalam lubang yang sudah dibuat sebelumnya, tutup secara perlahan, dan lakukan penyiraman.
                """,
                "https://lendah.kulonprogokab.go.id/detil/310/budidaya-pisang-rumahan"
            ),
            Article(
                "Tomat",
                """
                    Tomat (Lycopersicon esculentum mill.) adalah komoditas hortikulta yang penting karena harganya cukup baik dan banyak dikonsumsi masyarakat. Tomat dapat dikonsumsi sebagai sayur dan buah segar maupun di konsumsi dalam bentuk olahan seperti saus tomat. Sebagai sayuran atau buah, tomat merupakan sumber vitamin A dan C. Tomat dapat ditanam di dataran rendah hingga tinggi dan dapat di tanam sebagai tanaman tumpang sari dan rotasi pada lahan sawah.
                    SYARAT TUMBUH:
                    Tanah yang subur, gembur, porus, dengan kemasamaan tanah (pH) 5-7.
                    Curah hujan 750 – 1.250 mm/tahun dan kelembaban relative 25%
                    Tomat tumbuh baik pada suhu 20-27°C, pembentukan buah terhambat pada suhu >30°C atau <10°C.
                """,
                "https://www.google.com/amp/s/dpkp.brebeskab.go.id/477/budidaya-tomat/%3famp=1"
            ),
            Article(
                "Nanas",
                """
                    Nanas merupakan salah satu tanaman yang dapat tumbuh di iklim kering maupun basah dengan curah hujan tinggi maupun rendah sekalipun yang membuat budidaya nanas tidak mengalami kendala berarti baik itu terkait masalah lahan maupun cuaca. Nanas dapat tumbuh subur pada suhu antara 23-32°C dengan cahaya matahari yang berkisar 33-71%. Meskipun demikian, budidaya nanas juga tidak dapat dilakukan di sembarang tempat, karena lahan penanaman harus memiliki pH antara 4,5-5,5 tidak boleh kurang ataupun lebih karena akan mengganggu pertumbuhan tanaman. Selain itu lahan yang paling bagus untuk budidaya nanas juga cenderung pada jenis tanah yang gembur, tanah lempung berpasir, dan mengandung banyak unsur hara.
                """,
                "https://bibitbunga.com/cara-menanam-nanas-yang-baik-dan-benar"
            ),
            Article(
                "Pepaya",
                """
                    Tanaman Pepaya California dapat tumbuh dengan subur pada tanah yang subur dan sedikit berpasir. Oleh karena itu tanaman pepaya dapat tumbuh subur pada lahan gambut. Lahan yang akan digunakan untuk menanam tanaman papaya baiknya memiliki persyaratan, lahan terbuka (Full fotocintesis), memiliki drainase yang baik, pH tanah antara 6 – 7, suhu berkisar antara 25 – 30°C. Terletak pada ketinggian 300 – 500 m dpl. Curah hujan tahunan : 1000 – 2000 mm/thn. Kelembaban udara sekitar 40%.
                    Varietas pepaya California tidak memiliki syarat tumbuh yang spesifik karena varietas ini dapat tumbuh optimal di daerah dataran rendah, tengah, dan pegunungan. Pepaya California dapat dibudidayakan kapan saja, baik pada musim hujan maupun musim kemarau. Tanah yang baik untuk menanam pepaya California adalah tanah yang gembur, sedikit berpasir, dan kaya humus. Varietas ini paling baik ditanam di tanah berdrainase baik yang tidak tergenang air atau berlumpur. pH ideal untuk pepaya California adalah antara 6,0 dan 7,0 (netral) dan membutuhkan sinar matahari penuh.
                """,
                "https://pertanian.uma.ac.id/cara-budidaya-pepaya-california"
            )
        )
    }


    // Function to get dummy article data
    private fun getDummyArticleData(): List<Article> {
        // Mengembalikan data dummy untuk diinisialisasi dalam adapter
        return listOf(
            Article(
                "Pohon Pisang",
                """
                    Pohon pisang termasuk adaptif terhadap segala tempat, ia bisa tumbuh subur di daerah dingin maupun panas. Pohon pisang juga termasuk yang mudah dalam perawatannya, sehingga biaya budidaya pisang tergolong murah dibandingkan dengan jenis pohon buah yang lainnya.
                    Sama seperti penanaman bibit pada umumnya, sebaiknya penanaman dilakukan pada saat hujan sudah turun atau menjelang musim hujan. Lakukan penanaman pada sore hari agar bibit tidak strees terhadap lingkungan baru. Masukkan bibit ke dalam lubang yang sudah dibuat sebelumnya, tutup secara perlahan, dan lakukan penyiraman.
                """,
                "https://lendah.kulonprogokab.go.id/detil/310/budidaya-pisang-rumahan"
            ),
            Article(
                "Tomat",
                """
                    Tomat (Lycopersicon esculentum mill.) adalah komoditas hortikulta yang penting karena harganya cukup baik dan banyak dikonsumsi masyarakat. Tomat dapat dikonsumsi sebagai sayur dan buah segar maupun di konsumsi dalam bentuk olahan seperti saus tomat. Sebagai sayuran atau buah, tomat merupakan sumber vitamin A dan C. Tomat dapat ditanam di dataran rendah hingga tinggi dan dapat di tanam sebagai tanaman tumpang sari dan rotasi pada lahan sawah.
                    SYARAT TUMBUH:
                    Tanah yang subur, gembur, porus, dengan kemasamaan tanah (pH) 5-7.
                    Curah hujan 750 – 1.250 mm/tahun dan kelembaban relative 25%
                    Tomat tumbuh baik pada suhu 20-27°C, pembentukan buah terhambat pada suhu >30°C atau <10°C.
                """,
                "https://www.google.com/amp/s/dpkp.brebeskab.go.id/477/budidaya-tomat/%3famp=1"
            ),
            Article(
                "Nanas",
                """
                    Nanas merupakan salah satu tanaman yang dapat tumbuh di iklim kering maupun basah dengan curah hujan tinggi maupun rendah sekalipun yang membuat budidaya nanas tidak mengalami kendala berarti baik itu terkait masalah lahan maupun cuaca. Nanas dapat tumbuh subur pada suhu antara 23-32°C dengan cahaya matahari yang berkisar 33-71%. Meskipun demikian, budidaya nanas juga tidak dapat dilakukan di sembarang tempat, karena lahan penanaman harus memiliki pH antara 4,5-5,5 tidak boleh kurang ataupun lebih karena akan mengganggu pertumbuhan tanaman. Selain itu lahan yang paling bagus untuk budidaya nanas juga cenderung pada jenis tanah yang gembur, tanah lempung berpasir, dan mengandung banyak unsur hara.
                """,
                "https://bibitbunga.com/cara-menanam-nanas-yang-baik-dan-benar"
            ),
            Article(
                "Pepaya",
                """
                    Tanaman Pepaya California dapat tumbuh dengan subur pada tanah yang subur dan sedikit berpasir. Oleh karena itu tanaman pepaya dapat tumbuh subur pada lahan gambut. Lahan yang akan digunakan untuk menanam tanaman papaya baiknya memiliki persyaratan, lahan terbuka (Full fotocintesis), memiliki drainase yang baik, pH tanah antara 6 – 7, suhu berkisar antara 25 – 30°C. Terletak pada ketinggian 300 – 500 m dpl. Curah hujan tahunan : 1000 – 2000 mm/thn. Kelembaban udara sekitar 40%.
                    Varietas pepaya California tidak memiliki syarat tumbuh yang spesifik karena varietas ini dapat tumbuh optimal di daerah dataran rendah, tengah, dan pegunungan. Pepaya California dapat dibudidayakan kapan saja, baik pada musim hujan maupun musim kemarau. Tanah yang baik untuk menanam pepaya California adalah tanah yang gembur, sedikit berpasir, dan kaya humus. Varietas ini paling baik ditanam di tanah berdrainase baik yang tidak tergenang air atau berlumpur. pH ideal untuk pepaya California adalah antara 6,0 dan 7,0 (netral) dan membutuhkan sinar matahari penuh.
                """,
                "https://pertanian.uma.ac.id/cara-budidaya-pepaya-california"
            )
        )
    }

    private fun filterArticles(query: String) {
        // Logika untuk memfilter artikel berdasarkan query
        val filteredArticles = getActualArticleData().filter { article ->
            article.title.contains(query, true)
        }

        // Gunakan submitList untuk memperbarui data adapter
        articleAdapter.submitList(filteredArticles)
    }
}

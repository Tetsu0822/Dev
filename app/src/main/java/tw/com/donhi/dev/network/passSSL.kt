package tw.com.donhi.dev.network

import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object passSSL {
    @Throws(Exception::class)
    fun trustAllHttpsCertificates() {
        val trustAllCerts = arrayOfNulls<TrustManager>(1)
        val tm: TrustManager = miTM()
        trustAllCerts[0] = tm
        val sc = SSLContext.getInstance("SSL")
        sc.init(null, trustAllCerts, null)
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
    }

    /**
     * 忽略HTTPS请求的SSL证书，必须在openConnection之前调用
     * @throws Exception
     */
    @Throws(Exception::class)
    fun ignoreSsl() {
        val hv =
            HostnameVerifier { urlHostName, session -> true }
        trustAllHttpsCertificates()
        HttpsURLConnection.setDefaultHostnameVerifier(hv)
    }

    internal class miTM : TrustManager, X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate>? {
            return null
        }

        fun isServerTrusted(certs: Array<X509Certificate?>?): Boolean {
            return true
        }

        fun isClientTrusted(certs: Array<X509Certificate?>?): Boolean {
            return true
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {
            return
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {
            return
        }
    }
}
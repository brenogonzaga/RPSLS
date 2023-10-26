package ifam.edu.br.pdm.jokenpolagartospock

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var player1: ImageView
    private lateinit var player2: ImageView
    private lateinit var buttonPedra: ImageButton
    private lateinit var buttonPapel: ImageButton
    private lateinit var buttonTesoura: ImageButton
    private lateinit var buttonLargato: ImageButton
    private lateinit var buttonSpock: ImageButton

    private var jogada1 = 0
    private var jogada2 = 0

    private lateinit var some: AlphaAnimation
    private lateinit var aparece: AlphaAnimation

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player1 = findViewById(R.id.imageViewP1)
        player2 = findViewById(R.id.imageViewP2)
        buttonPedra = findViewById(R.id.buttonPedra)
        buttonPapel = findViewById(R.id.buttonPapel)
        buttonTesoura = findViewById(R.id.buttonTesoura)
        buttonLargato = findViewById(R.id.buttonLargato)
        buttonSpock = findViewById(R.id.buttonSpock)
        some = AlphaAnimation(1f, 0f)
        aparece = AlphaAnimation(0f, 1f)

        some.duration = 1500
        aparece.duration = 250

        some.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                player2.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                player2.visibility = View.INVISIBLE
                player2.startAnimation(aparece)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })

        aparece.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                sorteioJogadaInimigo()
                player2.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                verificaJogada()
                player2.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }

    fun toqueBotao(view: View) {
        tocaSom()
        player1.scaleX = -1f

        when (view.id) {
            R.id.buttonPedra -> {
                player1.setImageResource(R.drawable.pedra)
                jogada1 = 1
            }

            R.id.buttonPapel -> {
                player1.setImageResource(R.drawable.papel)
                jogada1 = 2
            }

            R.id.buttonTesoura -> {
                player1.setImageResource(R.drawable.tesoura)
                jogada1 = 3
            }

            R.id.buttonLargato -> {
                player1.setImageResource(R.drawable.largato)
                jogada1 = 4
            }

            R.id.buttonSpock -> {
                player1.setImageResource(R.drawable.spock)
                jogada1 = 5
            }
        }

        player2.startAnimation(some)
        player2.setImageResource(R.drawable.interrogacao)
    }

    fun sorteioJogadaInimigo() {
        val r = Random()

        when (r.nextInt(5)) {
            0 -> {
                player2.setImageResource(R.drawable.pedra)
                jogada2 = 1
            }

            1 -> {
                player2.setImageResource(R.drawable.papel)
                jogada2 = 2
            }

            2 -> {
                player2.setImageResource(R.drawable.tesoura)
                jogada2 = 3
            }

            3 -> {
                player2.setImageResource(R.drawable.largato)
                jogada2 = 4
            }

            4 -> {
                player2.setImageResource(R.drawable.spock)
                jogada2 = 5
            }
        }
    }

    fun verificaJogada() {
        val resultado = verificarResultado(jogada1, jogada2)
        val mensagem = when (resultado) {
            Resultado.EMPATE -> "Empate!"
            Resultado.VITORIA -> "Você ganhou!"
            Resultado.DERROTA -> "Você perdeu!"
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    fun verificarResultado(jogada1: Int, jogada2: Int): Resultado {
        return when {
            jogada1 == jogada2 -> Resultado.EMPATE
            verificaRegra(jogada1, jogada2) -> Resultado.VITORIA
            verificaRegra(jogada2, jogada1) -> Resultado.DERROTA
            else -> throw IllegalArgumentException("Jogadas inválidas")
        }
    }

    fun verificaRegra(jogada1: Int, jogada2: Int): Boolean {
        return when (jogada1) {
            1 -> jogada2 in listOf(3, 4)
            2 -> jogada2 in listOf(1, 5)
            3 -> jogada2 in listOf(2, 4)
            4 -> jogada2 in listOf(2, 5)
            5 -> jogada2 in listOf(1, 3)
            else -> false
        }
    }

    enum class Resultado {
        EMPATE, VITORIA, DERROTA
    }

    private fun tocaSom() {
        mediaPlayer?.start()
    }
}
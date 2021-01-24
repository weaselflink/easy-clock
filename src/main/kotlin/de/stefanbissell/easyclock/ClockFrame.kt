package de.stefanbissell.easyclock

import de.stefanbissell.easyclock.Settings.Companion.loadSettings
import javax.swing.JFrame
import javax.swing.JLabel
import java.awt.Font
import java.awt.Color
import javax.swing.border.EmptyBorder
import java.text.SimpleDateFormat
import java.awt.Point
import java.awt.event.*
import java.util.*
import javax.swing.Timer
import kotlin.math.max
import kotlin.system.exitProcess

class ClockFrame : JFrame(), ActionListener {

    private val clockFont = "Courier New"

    private val settings = loadSettings()
    private val label = createLabel().apply {
        text = timeString
    }
    private val timeString: String
        get() = SimpleDateFormat("HH:mm").format(Date())

    init {
        contentPane.add(label)
        defaultCloseOperation = EXIT_ON_CLOSE
        isUndecorated = true
        pack()
        setBounds(settings.x, settings.y, bounds.width, bounds.height)
        val adapter = ClockMouseAdapter()
        addMouseListener(adapter)
        addMouseMotionListener(adapter)
        addMouseWheelListener(adapter)
        Timer(100, this).apply {
            isRepeats = true
            start()
        }
    }

    private fun createLabel(): JLabel =
        JLabel().apply {
            horizontalAlignment = JLabel.CENTER
            font = Font(clockFont, Font.BOLD, settings.fontSize)
            foreground = Color.red
            background = Color.black
            isOpaque = true
            border = EmptyBorder(10, 10, 10, 10)
        }

    override fun actionPerformed(e: ActionEvent) {
        label.text = timeString
    }

    private fun resizeLabelFont(diff: Int) {
        settings.fontSize = max(10, settings.fontSize + diff * 5)
        label.font = Font(clockFont, Font.BOLD, settings.fontSize)
        pack()
    }

    private fun saveSettingsAndExit() {
        settings.save()
        exitProcess(0)
    }

    private inner class ClockMouseAdapter : MouseAdapter() {

        private var dragStart: Point? = null
        override fun mousePressed(e: MouseEvent) {
            dragStart = Point(e.x, e.y)
        }

        override fun mouseReleased(e: MouseEvent) {
            if (e.button == MouseEvent.BUTTON3) {
                saveSettingsAndExit()
            }
            dragStart = null
        }

        override fun mouseWheelMoved(e: MouseWheelEvent) {
            resizeLabelFont(e.wheelRotation)
        }

        override fun mouseDragged(e: MouseEvent) {
            val bounds = bounds
            val diffX = e.x - dragStart!!.x
            val diffY = e.y - dragStart!!.y
            settings.x = bounds.x + diffX
            settings.y = bounds.y + diffY
            setBounds(settings.x, settings.y, bounds.width, bounds.height)
        }
    }
}

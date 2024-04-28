package com.sudoku

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.widget.ImageView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.navigation.findNavController


class LaunchScreen : Fragment() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var soundMediaPlayer1: MediaPlayer
    private lateinit var sharedPreferences: SharedPreferences
    private var isSoundOff : Boolean = false
    private var isMuted : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_launch_screen, container, false)
        sharedPreferences = requireContext().getSharedPreferences("SudokuPrefs", Context.MODE_PRIVATE)
        isSoundOff = sharedPreferences.getBoolean("isSoundOff", true)
        isMuted = sharedPreferences.getBoolean("isMuted", true)
        saveIsSoundOffToPrefs(isSoundOff)
        saveIsMutedToPrefs(isMuted)
        mediaPlayer = MediaPlayerManager.getMediaPlayer(requireActivity())
        soundMediaPlayer1 =  MediaPlayer.create(requireContext(), R.raw.button_sound)

        mediaPlayer.start()
        if(isMuted){
            mediaPlayer.setVolume(0f,0f)
        }
        val playButton = view.findViewById<Button>(R.id.play)
        playButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_launchScreen_to_difficultyFragment)
            if(!isSoundOff){
                soundMediaPlayer1.start()
            }

        }

        val aboutButton = view.findViewById<Button>(R.id.about_button)
        aboutButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_launchScreen_to_aboutFragment)
            if(!isSoundOff) {
                soundMediaPlayer1.start()
            }
        }
        val solveButton = view.findViewById<Button>(R.id.solver)
        solveButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_launchScreen_to_solverFragment)
            if(!isSoundOff) {
                soundMediaPlayer1.start()
            }
        }
        val soundButton = view.findViewById<ImageView>(R.id.sound_btn)
        soundButton.setOnClickListener {
            showConformation()
            if(!isSoundOff) {
                soundMediaPlayer1.start()
            }
        }
        return view
    }

    private fun showConformation() {
        val dialog = Dialog(requireContext(), R.style.CustomDialogTheme)
        dialog.setContentView(R.layout.music_popup)
        val sound_switch : Switch = dialog.findViewById(R.id.music_switch)
        updateMuteSwitch(sound_switch)
        sound_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                isMuted = true
            }else{
                isMuted = false
            }
            toggleMuteState()
            updateMuteSwitch(sound_switch)

        }
        val music_switch : Switch = dialog.findViewById(R.id.sound_swich)
        updateSoundOffSwitch(music_switch)
        music_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                isSoundOff = false
            } else {
                isSoundOff = true
            }
            updateSoundOffSwitch(music_switch)
            saveIsSoundOffToPrefs(isSoundOff)
        }

        dialog.show()

    }

    private fun toggleMuteState() {
        if (isMuted) {
            mediaPlayer.setVolume(0.7f, 0.7f)
        } else {
            mediaPlayer.setVolume(0f, 0f)
        }
        isMuted = !isMuted
        saveIsMutedToPrefs(isMuted)
    }

    private fun saveIsSoundOffToPrefs(isSOff: Boolean) {
        sharedPreferences.edit().putBoolean("isSoundOff", isSOff).apply()
    }

    private fun saveIsMutedToPrefs(isMute: Boolean) {
        sharedPreferences.edit().putBoolean("isMuted", isMute).apply()
    }

    private fun updateMuteSwitch(music_switch : Switch) {
        if(isMuted){
            music_switch.isChecked = false
        }else{
            music_switch.isChecked = true
        }
    }

    private fun updateSoundOffSwitch(sound_switch : Switch) {
        if(isSoundOff){
            sound_switch.isChecked = false
        }else{
            sound_switch.isChecked = true
        }
    }



}

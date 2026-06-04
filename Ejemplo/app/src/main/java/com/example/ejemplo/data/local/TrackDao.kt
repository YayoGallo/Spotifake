package com.example.ejemplo.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Query("SELECT * FROM local_tracks WHERE userId = :userId")
    fun getAllTracks(userId: String): Flow<List<LocalTrack>>

    @Query("SELECT * FROM local_tracks WHERE isFavorite = 1 AND userId = :userId")
    fun getFavorites(userId: String): Flow<List<LocalTrack>>

    @Query("SELECT * FROM local_tracks WHERE id = :trackId")
    suspend fun getTrackById(trackId: Long): LocalTrack?

    @Query("SELECT * FROM local_tracks WHERE titulo = :titulo AND artista = :artista AND userId = :userId LIMIT 1")
    suspend fun findTrack(titulo: String, artista: String, userId: String): LocalTrack?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: LocalTrack)

    @Update
    suspend fun updateTrack(track: LocalTrack)

    @Query("UPDATE local_tracks SET isFavorite = :isFavorite WHERE id = :trackId")
    suspend fun updateFavoriteStatus(trackId: Long, isFavorite: Boolean)

    @Query("SELECT EXISTS(SELECT 1 FROM local_tracks WHERE titulo = :titulo AND artista = :artista AND userId = :userId AND isFavorite = 1)")
    fun isTrackFavorite(titulo: String, artista: String, userId: String): kotlinx.coroutines.flow.Flow<Boolean>

    @Delete
    suspend fun deleteTrack(track: LocalTrack)

    @Query("DELETE FROM local_tracks WHERE titulo = :titulo AND artista = :artista AND userId = :userId")
    suspend fun deleteTrackByDetails(titulo: String, artista: String, userId: String)
}

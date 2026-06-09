package core.architecture

import core.network.networkexception.UnauthorizedException
import core.security.SessionManager

abstract class BaseRepository(
    private val sessionManager: SessionManager
) {
    protected suspend fun <T> execute(
        block: suspend () -> T
    ): Result<T> {
        return try {
            Result.success(block())
        } catch (e: UnauthorizedException) {
            sessionManager.onSessionExpired()
            sessionManager.getToken()
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
package com.vaticle.typedb.studio.db

import com.vaticle.typedb.common.collection.Either

class QueryResponseStream(@Volatile var completed: Boolean = false) {

    var lastFetchedNanos: Long = 0
    private var vertexStore: MutableList<VertexData> = mutableListOf()
    private var edgeStore: MutableList<EdgeData> = mutableListOf()
    private var exception: Exception? = null

    @Synchronized
    fun putVertex(vertex: VertexData) {
        if (!completed) vertexStore += vertex
    }

    @Synchronized
    fun putEdge(edge: EdgeData) {
        if (!completed) edgeStore += edge
    }

    @Synchronized
    fun putError(exception: Exception) {
        if (this.completed) return
        this.exception = exception
        println("Putting Exception to QueryResponseStream")
        completed = true
    }

    @Synchronized
    fun drain(): Either<GraphData, Exception> {
        return when (exception) {
            null -> {
                // TODO: try simply emptying the lists rather than re-assigning them, though I'm fairly sure it won't work
                val data = GraphData(vertexStore, edgeStore)
                vertexStore = mutableListOf()
                edgeStore = mutableListOf()
                Either.first(data)
            }
            else -> Either.second(exception)
        }
    }

    companion object {
        val EMPTY = QueryResponseStream(completed = true)
    }
}

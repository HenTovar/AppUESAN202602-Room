package dev.lchang.appuesan.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage

object SupabaseManager {

    private const val SUPABASE_URL = "https://wzxkcchfvhimzyahanhi.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind6eGtjY2hmdmhpbXp5YWhhbmhpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTIyNjA5OTEsImV4cCI6MjEwNzgzNjk5MX0.qdjeA9Zt0t79NKXFtHmks7991WBaFy_cfeDHhp7B9e4"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ){
        install(Storage)
    }

}
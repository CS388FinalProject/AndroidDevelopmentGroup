package com.example.nextflix.data.personality

data class PersonalityPreferenceProfile(
    val movieGenres: List<String> = emptyList(),
    val bookGenres: List<String> = emptyList(),
    val tones: List<String> = emptyList(),
    val pacing: List<String> = emptyList(),
    val themes: List<String> = emptyList(),
    val settings: List<String> = emptyList()
) {
    fun isEmpty(): Boolean {
        return movieGenres.isEmpty() &&
            bookGenres.isEmpty() &&
            tones.isEmpty() &&
            pacing.isEmpty() &&
            themes.isEmpty() &&
            settings.isEmpty()
    }
}

object PersonalityPreferenceMapper {

    fun map(result: PersonalityQuizResult?): PersonalityPreferenceProfile {
        if (result == null) return PersonalityPreferenceProfile()

        val movieGenres = linkedSetOf<String>()
        val bookGenres = linkedSetOf<String>()
        val tones = linkedSetOf<String>()
        val pacing = linkedSetOf<String>()
        val themes = linkedSetOf<String>()
        val settings = linkedSetOf<String>()

        result.answers.values.forEach { optionId ->
            val rule = OPTION_RULES[optionId] ?: return@forEach
            movieGenres.addAll(rule.movieGenres)
            bookGenres.addAll(rule.bookGenres)
            tones.addAll(rule.tones)
            pacing.addAll(rule.pacing)
            themes.addAll(rule.themes)
            settings.addAll(rule.settings)
        }

        return PersonalityPreferenceProfile(
            movieGenres = movieGenres.toList(),
            bookGenres = bookGenres.toList(),
            tones = tones.toList(),
            pacing = pacing.toList(),
            themes = themes.toList(),
            settings = settings.toList()
        )
    }

    fun toPromptBlock(profile: PersonalityPreferenceProfile): String {
        if (profile.isEmpty()) return "No strong personality-derived preferences available."

        return buildString {
            appendLine("Mapped Personality Preferences:")
            appendLine("- Movie genres: ${format(profile.movieGenres)}")
            appendLine("- Book genres: ${format(profile.bookGenres)}")
            appendLine("- Story tones: ${format(profile.tones)}")
            appendLine("- Pacing: ${format(profile.pacing)}")
            appendLine("- Themes: ${format(profile.themes)}")
            append("- Settings: ${format(profile.settings)}")
        }
    }

    private fun format(values: List<String>): String {
        return if (values.isEmpty()) "No strong signal" else values.joinToString(", ")
    }

    private data class MappingRule(
        val movieGenres: List<String> = emptyList(),
        val bookGenres: List<String> = emptyList(),
        val tones: List<String> = emptyList(),
        val pacing: List<String> = emptyList(),
        val themes: List<String> = emptyList(),
        val settings: List<String> = emptyList()
    )

    private fun rule(
        movieGenres: List<String> = emptyList(),
        bookGenres: List<String> = emptyList(),
        tones: List<String> = emptyList(),
        pacing: List<String> = emptyList(),
        themes: List<String> = emptyList(),
        settings: List<String> = emptyList()
    ): MappingRule {
        return MappingRule(
            movieGenres = movieGenres,
            bookGenres = bookGenres,
            tones = tones,
            pacing = pacing,
            themes = themes,
            settings = settings
        )
    }

    // This table is the single source of truth for personality-to-preference translation.
    private val OPTION_RULES: Map<String, MappingRule> = mapOf(
        "ft_social" to rule(
            movieGenres = listOf("Comedy", "Family", "Romance"),
            bookGenres = listOf("Romance", "Contemporary", "Friendship"),
            tones = listOf("warm"),
            pacing = listOf("steady"),
            themes = listOf("friendship", "community")
        ),
        "ft_creative" to rule(
            movieGenres = listOf("Drama", "Fantasy", "Documentary"),
            bookGenres = listOf("Fantasy", "Literary Fiction", "Biography"),
            tones = listOf("reflective"),
            pacing = listOf("steady"),
            themes = listOf("creativity", "self-discovery")
        ),
        "ft_calm" to rule(
            movieGenres = listOf("Drama", "Mystery"),
            bookGenres = listOf("Literary Fiction", "Mystery"),
            tones = listOf("calm", "introspective"),
            pacing = listOf("slow"),
            themes = listOf("healing", "reflection")
        ),
        "ft_active" to rule(
            movieGenres = listOf("Action", "Adventure", "Thriller"),
            bookGenres = listOf("Thriller", "Adventure", "Science Fiction"),
            tones = listOf("exciting"),
            pacing = listOf("fast"),
            themes = listOf("adrenaline", "momentum")
        ),

        "str_planned" to rule(
            movieGenres = listOf("Mystery", "Crime"),
            bookGenres = listOf("Mystery", "Historical Fiction"),
            tones = listOf("focused"),
            pacing = listOf("steady"),
            themes = listOf("strategy", "order")
        ),
        "str_flexible" to rule(
            movieGenres = listOf("Drama", "Adventure"),
            bookGenres = listOf("Contemporary", "Adventure"),
            tones = listOf("balanced"),
            pacing = listOf("steady"),
            themes = listOf("adaptability")
        ),
        "str_spontaneous" to rule(
            movieGenres = listOf("Action", "Comedy"),
            bookGenres = listOf("Adventure", "Thriller"),
            tones = listOf("playful"),
            pacing = listOf("fast"),
            themes = listOf("spontaneity")
        ),
        "str_mix" to rule(
            movieGenres = listOf("Drama", "Comedy"),
            bookGenres = listOf("Contemporary", "Literary Fiction"),
            tones = listOf("balanced"),
            pacing = listOf("steady"),
            themes = listOf("balance")
        ),

        "pace_slow" to rule(
            movieGenres = listOf("Drama", "Mystery"),
            bookGenres = listOf("Literary Fiction", "Historical Fiction"),
            tones = listOf("reflective"),
            pacing = listOf("slow"),
            themes = listOf("character-study")
        ),
        "pace_steady" to rule(
            movieGenres = listOf("Drama", "Mystery"),
            bookGenres = listOf("Mystery", "Contemporary"),
            tones = listOf("balanced"),
            pacing = listOf("steady")
        ),
        "pace_brisk" to rule(
            movieGenres = listOf("Adventure", "Thriller"),
            bookGenres = listOf("Thriller", "Fantasy"),
            tones = listOf("engaging"),
            pacing = listOf("brisk")
        ),
        "pace_intense" to rule(
            movieGenres = listOf("Action", "Thriller", "Horror"),
            bookGenres = listOf("Thriller", "Science Fiction", "Horror"),
            tones = listOf("intense"),
            pacing = listOf("fast"),
            themes = listOf("high-stakes")
        ),

        "tone_emotional" to rule(
            movieGenres = listOf("Drama", "Romance"),
            bookGenres = listOf("Romance", "Literary Fiction"),
            tones = listOf("emotional"),
            pacing = listOf("steady"),
            themes = listOf("relationships", "personal-growth")
        ),
        "tone_adventure" to rule(
            movieGenres = listOf("Adventure", "Action", "Fantasy"),
            bookGenres = listOf("Adventure", "Fantasy", "Science Fiction"),
            tones = listOf("adventurous"),
            pacing = listOf("fast"),
            themes = listOf("quest", "discovery")
        ),
        "tone_balanced" to rule(
            movieGenres = listOf("Drama", "Adventure"),
            bookGenres = listOf("Contemporary", "Fantasy"),
            tones = listOf("balanced"),
            pacing = listOf("steady"),
            themes = listOf("variety")
        ),
        "tone_surprise" to rule(
            movieGenres = listOf("Mystery", "Thriller", "Science Fiction"),
            bookGenres = listOf("Mystery", "Thriller", "Science Fiction"),
            tones = listOf("surprising"),
            pacing = listOf("brisk"),
            themes = listOf("twists", "novelty")
        ),

        "set_real" to rule(
            movieGenres = listOf("Drama", "Crime"),
            bookGenres = listOf("Contemporary", "Literary Fiction"),
            themes = listOf("realism"),
            settings = listOf("real-world")
        ),
        "set_imaginative" to rule(
            movieGenres = listOf("Fantasy", "Science Fiction"),
            bookGenres = listOf("Fantasy", "Science Fiction"),
            themes = listOf("worldbuilding"),
            settings = listOf("imaginative")
        ),
        "set_historical" to rule(
            movieGenres = listOf("Historical", "Drama"),
            bookGenres = listOf("Historical Fiction", "Biography"),
            themes = listOf("history"),
            settings = listOf("historical")
        ),
        "set_any" to rule(
            tones = listOf("flexible"),
            themes = listOf("variety")
        ),

        "goal_comfort" to rule(
            movieGenres = listOf("Comedy", "Family", "Romance"),
            bookGenres = listOf("Romance", "Cozy Mystery", "Contemporary"),
            tones = listOf("uplifting"),
            pacing = listOf("steady"),
            themes = listOf("hope", "comfort")
        ),
        "goal_thought" to rule(
            movieGenres = listOf("Drama", "Science Fiction"),
            bookGenres = listOf("Literary Fiction", "Science Fiction", "Nonfiction"),
            tones = listOf("thought-provoking"),
            pacing = listOf("steady"),
            themes = listOf("ideas", "ethics")
        ),
        "goal_both" to rule(
            movieGenres = listOf("Drama", "Comedy"),
            bookGenres = listOf("Contemporary", "Literary Fiction"),
            tones = listOf("balanced"),
            themes = listOf("range")
        ),
        "goal_escape" to rule(
            movieGenres = listOf("Fantasy", "Adventure"),
            bookGenres = listOf("Fantasy", "Adventure", "Science Fiction"),
            tones = listOf("escapist"),
            pacing = listOf("brisk"),
            themes = listOf("escape")
        ),

        "soc_solo" to rule(
            movieGenres = listOf("Drama", "Mystery"),
            bookGenres = listOf("Literary Fiction", "Mystery"),
            tones = listOf("introspective"),
            pacing = listOf("slow"),
            themes = listOf("self-reflection")
        ),
        "soc_partner" to rule(
            movieGenres = listOf("Romance", "Drama"),
            bookGenres = listOf("Romance", "Contemporary"),
            tones = listOf("warm"),
            pacing = listOf("steady"),
            themes = listOf("connection")
        ),
        "soc_group" to rule(
            movieGenres = listOf("Comedy", "Action", "Adventure"),
            bookGenres = listOf("Thriller", "Adventure"),
            tones = listOf("energetic"),
            pacing = listOf("fast"),
            themes = listOf("teamwork")
        ),
        "soc_either" to rule(
            movieGenres = listOf("Drama", "Comedy"),
            bookGenres = listOf("Contemporary", "Mystery"),
            tones = listOf("balanced"),
            themes = listOf("flexibility")
        ),

        "com_short" to rule(
            movieGenres = listOf("Comedy", "Thriller"),
            bookGenres = listOf("Mystery", "Novella"),
            pacing = listOf("fast"),
            themes = listOf("compact")
        ),
        "com_medium" to rule(
            movieGenres = listOf("Drama", "Adventure"),
            bookGenres = listOf("Contemporary", "Fantasy"),
            pacing = listOf("steady"),
            themes = listOf("balanced-length")
        ),
        "com_long" to rule(
            movieGenres = listOf("Fantasy", "Drama", "Historical"),
            bookGenres = listOf("Fantasy", "Historical Fiction", "Science Fiction"),
            pacing = listOf("slow"),
            themes = listOf("immersive-worlds")
        ),
        "com_varies" to rule(
            tones = listOf("flexible"),
            pacing = listOf("varied"),
            themes = listOf("variety")
        )
    )
}

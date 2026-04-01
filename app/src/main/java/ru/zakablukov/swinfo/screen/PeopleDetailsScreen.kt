package ru.zakablukov.swinfo.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.zakablukov.domain.model.People
import ru.zakablukov.swinfo.R
import ru.zakablukov.swinfo.enums.LoadState
import ru.zakablukov.swinfo.viewmodel.PeopleDetailsViewModel

@Composable
fun PeopleDetailsScreen(
    id: Int,
    onBackClick: () -> Unit,
    viewModel: PeopleDetailsViewModel = hiltViewModel()
) {
    val peopleResult by viewModel.peopleResult.collectAsStateWithLifecycle()
    val peopleLoadState by viewModel.peopleLoadState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadPeopleById(id)
    }

    PeopleDetailsScreenContent(peopleResult, peopleLoadState, onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleDetailsScreenContent(
    people: People?,
    loadState: LoadState?,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = people?.name ?: ""
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        when (loadState) {
            LoadState.LOADING -> {
                if (people == null) {
                    Log.d(
                        stringResource(R.string.tag_people_details),
                        stringResource(R.string.msg_loading)
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            LoadState.SUCCESS -> Log.d(
                stringResource(R.string.tag_people_details),
                stringResource(R.string.msg_success)
            )

            LoadState.ERROR -> {
                if (people == null) {
                    Log.d(
                        stringResource(R.string.tag_people_details),
                        stringResource(R.string.msg_error)
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        SectionTitle(stringResource(R.string.text_no_info_people))
                    }
                }
            }

            null -> Log.d(
                stringResource(R.string.tag_people_details),
                stringResource(R.string.msg_init)
            )
        }
        people?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { SectionTitle(stringResource(R.string.people_info_title)) }
                item { InfoBox(stringResource(R.string.people_name), people.name) }
                item { InfoBox(stringResource(R.string.people_height), "${people.height}cm") }
                item { InfoBox(stringResource(R.string.people_mass), "${people.mass}kg") }
                item { InfoBox(stringResource(R.string.people_hair_color), people.hairColor) }
                item { InfoBox(stringResource(R.string.people_skin_color), people.skinColor) }
                item { InfoBox(stringResource(R.string.people_eye_color), people.eyeColor) }
                item { InfoBox(stringResource(R.string.people_birth_year), people.birthYear) }
                item { InfoBox(stringResource(R.string.people_gender), people.gender) }
                item { InfoBox(stringResource(R.string.people_films), people.filmsId.toString()) }
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
    )
}

@Composable
fun InfoBox(title: String, text: String) {
    Column(modifier = Modifier.standardBoxStyle()) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

fun Modifier.standardBoxStyle(): Modifier = this
    .fillMaxWidth()
    .clip(RoundedCornerShape(8.dp))
    .background(Color.LightGray)
    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
    .padding(16.dp)

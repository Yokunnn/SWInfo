package ru.zakablukov.swinfo.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.zakablukov.domain.model.People
import ru.zakablukov.swinfo.R
import ru.zakablukov.swinfo.enums.LoadState
import ru.zakablukov.swinfo.ui.theme.SWInfoTheme
import ru.zakablukov.swinfo.viewmodel.PeopleListViewModel

@Composable
fun PeopleListScreen(
    onPeopleClick: (Int) -> Unit,
    viewModel: PeopleListViewModel = hiltViewModel()
) {
    val peopleAll by viewModel.peopleResult.collectAsStateWithLifecycle()
    val peopleLoadState by viewModel.peopleLoadState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadAllPeople()
    }

    PeopleListScreenContent(onPeopleClick, peopleAll, peopleLoadState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleListScreenContent(
    onPeopleClick: (Int) -> Unit,
    peopleAll: List<People>,
    loadState: LoadState?
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_swchars)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        when (loadState) {
            LoadState.LOADING -> {
                Log.d(stringResource(R.string.tag_people), stringResource(R.string.msg_loading))
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LoadState.SUCCESS -> {
                Log.d(
                    stringResource(R.string.tag_people),
                    stringResource(R.string.msg_success)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = peopleAll,
                        key = { people -> people.id }
                    ) { people ->
                        PeopleListItem(
                            people,
                            { onPeopleClick(people.id) }
                        )
                    }
                }
            }

            LoadState.ERROR -> {
                Log.d(stringResource(R.string.tag_people), stringResource(R.string.msg_error))
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    SectionTitle(stringResource(R.string.text_error_swcharslist))
                }
            }

            null -> Log.d(stringResource(R.string.tag_people), stringResource(R.string.msg_init))
        }
    }
}

@Composable
fun PeopleListItem(people: People, onPeopleClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
            .clickable { onPeopleClick(people.id) }
            .padding(16.dp)
    ) {
        Text(
            people.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "Height: ${people.height}cm, Mass: ${people.mass} kg, Hair: ${people.hairColor}, Eyes: ${people.eyeColor}",
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PeopleListScreenPreview() {
    SWInfoTheme() {
        PeopleListScreenContent(
            {},
            emptyList(),
            LoadState.SUCCESS
        )
    }
}
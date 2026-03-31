package ru.zakablukov.swinfo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.zakablukov.domain.model.People
import ru.zakablukov.swinfo.ui.theme.SWInfoTheme
import ru.zakablukov.swinfo.viewmodel.PeopleListViewModel

@Composable
fun PeopleListScreen(
    onPeopleClick: (Int) -> Unit,
    viewModel: PeopleListViewModel = hiltViewModel()
) {
    val peopleAll = viewModel.peopleResult.collectAsStateWithLifecycle()
    val peopleLoadState = viewModel.peopleLoadState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadAllPeople()
    }

    PeopleListScreenContent(onPeopleClick, peopleAll.value)
}

@Composable
fun PeopleListScreenContent(
    onPeopleClick: (Int) -> Unit,
    peopleAll: List<People>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
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

@Composable
fun PeopleListItem(people: People, onPeopleClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPeopleClick(people.id) }
            .padding(16.dp)
    ) {
        Text(people.name)
        Spacer(Modifier.height(4.dp))
        Text(people.gender)
    }
}

@Preview(showBackground = true)
@Composable
fun PeopleListScreenPreview() {
    SWInfoTheme() {
        PeopleListScreenContent(
            {},
            emptyList()
        )
    }
}
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import ru.zakablukov.domain.model.People
import ru.zakablukov.swinfo.R
import ru.zakablukov.swinfo.viewmodel.PeopleListViewModel

@Composable
fun PeopleListScreen(
    onPeopleClick: (Int) -> Unit,
    viewModel: PeopleListViewModel = hiltViewModel()
) {
    val lazyPagingPeople = viewModel.peopleResult.collectAsLazyPagingItems()

    PeopleListScreenContent(onPeopleClick, lazyPagingPeople)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleListScreenContent(
    onPeopleClick: (Int) -> Unit,
    lazyPagingPeople: LazyPagingItems<People>
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
        if (lazyPagingPeople.loadState.refresh is LoadState.Error && lazyPagingPeople.itemCount == 0) {
            Log.d(stringResource(R.string.tag_people), stringResource(R.string.msg_error))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SectionTitle(stringResource(R.string.text_error_swcharslist))
            }
        } else if (lazyPagingPeople.loadState.refresh is LoadState.Loading && lazyPagingPeople.itemCount == 0) {
            Log.d(stringResource(R.string.tag_people), stringResource(R.string.msg_loading))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Log.d(
                stringResource(R.string.tag_people),
                stringResource(R.string.msg_success)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    count = lazyPagingPeople.itemCount,
                    key = lazyPagingPeople.itemKey { people -> people.id }
                ) { index ->
                    val people = lazyPagingPeople[index]
                    people?.let {
                        PeopleListItem(
                            people,
                            { onPeopleClick(people.id) }
                        )
                    }
                }
            }
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
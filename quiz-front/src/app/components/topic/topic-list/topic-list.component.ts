import { Component, OnInit } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { Topic } from 'src/app/datamodel/topic';
import { TopicService } from 'src/app/services/topic.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {

  topics$: Topic[];
  topicsSearch$: Observable<Topic[]>;
  private searchTerms = new Subject<string>();
  private currentUser: User;

  constructor(private authService: AuthenticationService,
    private topicService: TopicService) { 
      this.topics$ =[];
      this.authService.currentUser.subscribe(x => this.currentUser = x);
    }

  ngOnInit() {
    this.getAllTopics();
    this.topicsSearch$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),
      // ignore new term if same as previous term
      distinctUntilChanged(),
      // switch to new search observable each time the term changes
      switchMap((term: string) => this.topicService.searchTopic(term)),
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  getAllTopics(): void{
    this.topicService.getAllTopics()
      .subscribe(topics => this.topics$ = topics);
  }

  deleteTopic(topicToDelete: Topic): void{
    this.topics$ = this.topics$.filter(topic => topic != topicToDelete);
    this.topicService.deleteTopic(topicToDelete).subscribe();
  }

}

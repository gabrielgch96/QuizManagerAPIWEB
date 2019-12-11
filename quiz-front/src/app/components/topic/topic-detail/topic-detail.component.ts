import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/datamodel/topic';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { TopicService } from 'src/app/services/topic.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-topic-detail',
  templateUrl: './topic-detail.component.html',
  styleUrls: ['./topic-detail.component.css']
})
export class TopicDetailComponent implements OnInit {

  topic: Topic;
  private currentUser: User;

  constructor(private route: ActivatedRoute,
    private authService: AuthenticationService,
    private topicService: TopicService,
    private location: Location) {
      this.authService.currentUser.subscribe(x => this.currentUser = x);
     }

  ngOnInit() {
    this.getTopic();
  }

  getTopic(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.topicService.getTopic(id).subscribe(
      topic => this.topic = topic
    );
  }

  goBack(): void{
    this.location.back();
  }

  updateTopic(){
    this.topicService.updateTopic(this.topic).subscribe(
      topic => this.topic = topic
    );
    this.goBack();
  }

}
